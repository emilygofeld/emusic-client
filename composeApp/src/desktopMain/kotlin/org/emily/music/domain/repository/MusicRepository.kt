package org.emily.music.domain.repository

import org.emily.core.constants.ID
import org.emily.music.domain.communication.MusicResponse

interface MusicRepository {
    suspend fun addSongToPlaylist(songId: ID, playlistId: ID): MusicResponse
    suspend fun removeSongFromPlaylist(songId: ID, playlistId: ID): MusicResponse
    suspend fun getPlaylist(playlistId: ID): MusicResponse
    suspend fun createPlaylistForUser(playlistTitle: String): MusicResponse
    suspend fun removePlaylistFromUser(playlistId: ID): MusicResponse
    suspend fun getUserPlaylists(userId: ID): MusicResponse
    suspend fun getSong(songId: ID): MusicResponse
    suspend fun getUserData(userId: ID): MusicResponse
    suspend fun getCurrentUserPlaylists(): MusicResponse
    suspend fun getCurrentUserData(): MusicResponse
}