package org.emily.music.data.repository

import org.emily.auth.domain.token.TokenService
import org.emily.core.constants.ID
import org.emily.music.domain.api.MusicApi
import org.emily.music.domain.communication.MusicRequest
import org.emily.music.domain.communication.MusicResponse
import org.emily.music.domain.repository.MusicRepository

class MusicRepositoryImpl(
    private val api: MusicApi,
    private val tokenService: TokenService<String>
): MusicRepository {

    override suspend fun addSongToPlaylist(songId: ID, playlistId: ID): MusicResponse {
        return api.sendApiRequest(MusicRequest.AddSongToPlaylist(songId = songId, playlistId = playlistId), getToken())
    }

    override suspend fun removeSongFromPlaylist(songId: ID, playlistId: ID): MusicResponse {
        return api.sendApiRequest(MusicRequest.DeleteSongFromPlaylist(songId = songId, playlistId = playlistId), getToken())
    }

    override suspend fun getPlaylist(playlistId: ID): MusicResponse {
        return api.sendApiRequest(MusicRequest.GetPlaylist(playlistId = playlistId), getToken())
    }

    override suspend fun createPlaylistForUser(playlistTitle: String): MusicResponse {
        return api.sendApiRequest(MusicRequest.CreatePlaylist(title = playlistTitle), getToken())
    }

    override suspend fun removePlaylistFromUser(playlistId: ID): MusicResponse {
        return api.sendApiRequest(MusicRequest.DeletePlaylist(playlistId = playlistId), getToken())
    }

    override suspend fun getUserPlaylists(userId: ID): MusicResponse {
        return api.sendApiRequest(MusicRequest.GetUserPlaylists(userId = userId), getToken())
    }

    override suspend fun getSong(songId: ID): MusicResponse {
        return api.sendApiRequest(MusicRequest.GetSong(songId = songId), getToken())
    }

    override suspend fun getUserData(userId: ID): MusicResponse {
        return api.sendApiRequest(MusicRequest.GetUserData(userId = userId), getToken())

    }

    override suspend fun getCurrentUserPlaylists(): MusicResponse {
        return api.sendApiRequest(MusicRequest.GetCurrUserPlaylists(), getToken())
    }

    override suspend fun getCurrentUserData(): MusicResponse {
        return api.sendApiRequest(MusicRequest.GetCurrUserData(), getToken())
    }

    private suspend fun getToken(): String {
        return tokenService.get() ?: throw IllegalStateException("Invalid or missing token")
    }
}