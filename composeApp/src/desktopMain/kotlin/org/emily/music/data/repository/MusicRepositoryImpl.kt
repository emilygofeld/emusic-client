package org.emily.music.data.repository

import org.emily.auth.domain.token.TokenService
import org.emily.core.constants.ID
import org.emily.music.domain.api.MusicApi
import org.emily.music.domain.communication.MusicRequest
import org.emily.music.domain.communication.MusicResponse
import org.emily.music.domain.repository.MusicRepository
import kotlin.reflect.KClass

class MusicRepositoryImpl(
    private val api: MusicApi,
    private val tokenService: TokenService<String>
): MusicRepository {

    override suspend fun addSongToPlaylist(songId: ID, playlistId: ID): MusicResponse {
        return getMusicResponse(
            MusicRequest.AddSongToPlaylist(songId = songId, playlistId = playlistId),
            MusicRequest.AddSongToPlaylist::class
        )
    }

    override suspend fun removeSongFromPlaylist(songId: ID, playlistId: ID): MusicResponse {
        return getMusicResponse(
            MusicRequest.DeleteSongFromPlaylist(songId = songId, playlistId = playlistId),
            MusicRequest.DeleteSongFromPlaylist::class
        )
    }

    override suspend fun getPlaylist(playlistId: ID): MusicResponse {
        return getMusicResponse(
            MusicRequest.GetPlaylist(playlistId = playlistId),
            MusicRequest.GetPlaylist::class
        )
    }

    override suspend fun createPlaylistForUser(playlistTitle: String): MusicResponse {
        return getMusicResponse(
            MusicRequest.CreatePlaylist(title = playlistTitle),
            MusicRequest.CreatePlaylist::class
        )
    }

    override suspend fun removePlaylistFromUser(playlistId: ID): MusicResponse {
        return getMusicResponse(
            MusicRequest.DeletePlaylist(playlistId = playlistId),
            MusicRequest.DeletePlaylist::class
        )
    }

    override suspend fun getUserPlaylists(userId: ID): MusicResponse {
        return getMusicResponse(
            MusicRequest.GetUserPlaylists(userId = userId),
            MusicRequest.GetUserPlaylists::class
        )
    }

    override suspend fun getSong(songId: ID): MusicResponse {
        return getMusicResponse(
            MusicRequest.GetSong(songId = songId),
            MusicRequest.GetSong::class
        )
    }

    override suspend fun getUserData(userId: ID): MusicResponse {
        return getMusicResponse(
            MusicRequest.GetUserData(userId = userId),
            MusicRequest.GetUserData::class
        )
    }

    override suspend fun getCurrentUserPlaylists(): MusicResponse {
        return getMusicResponse(
            MusicRequest.GetCurrUserPlaylists(),
            MusicRequest.GetCurrUserPlaylists::class
        )
    }

    override suspend fun getCurrentUserData(): MusicResponse {
        return getMusicResponse(
            MusicRequest.GetCurrUserData(),
            MusicRequest.GetCurrUserData::class
        )
    }

    private suspend fun <T : MusicRequest> getMusicResponse(
        musicRequest: T,
        type: KClass<T>
    ): MusicResponse {
        return try {
            api.sendApiRequest(
                musicRequest = musicRequest,
                type = type,
                token = getToken()
            )
        } catch (e: Exception) {
            MusicResponse.ErrorResponse(message = e.localizedMessage)
        }
    }

    private suspend fun getToken(): String {
        return tokenService.get() ?: throw IllegalStateException("Invalid or missing token")
    }
}