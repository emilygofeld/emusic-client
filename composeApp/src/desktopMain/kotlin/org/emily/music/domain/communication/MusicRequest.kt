package org.emily.music.domain.communication

import kotlinx.serialization.Serializable
import org.emily.core.constants.ID
import org.emily.core.constants.ProtocolCode

@Serializable
sealed class MusicRequest {
    abstract val code: Int

    @Serializable
    data class CreatePlaylist(override val code: Int = ProtocolCode.CREATE_PLAYLIST, val title: String): MusicRequest()
    @Serializable
    data class DeletePlaylist(override val code: Int = ProtocolCode.DELETE_PLAYLIST, val playlistId: ID): MusicRequest()
    @Serializable
    data class AddSongToPlaylist(override val code: Int = ProtocolCode.ADD_SONG_TO_PLAYLIST, val songId: ID, val playlistId: ID): MusicRequest()
    @Serializable
    data class DeleteSongFromPlaylist(override val code: Int = ProtocolCode.DELETE_SONG_FROM_PLAYLIST, val songId: ID, val playlistId: ID): MusicRequest()
    @Serializable
    data class GetPlaylist(override val code: Int = ProtocolCode.GET_PLAYLIST, val playlistId: ID): MusicRequest()
    @Serializable
    data class GetUserPlaylists(override val code: Int = ProtocolCode.GET_USER_PLAYLISTS, val userId: ID): MusicRequest()
    @Serializable
    data class GetSong(override val code: Int = ProtocolCode.GET_SONG, val songId: ID): MusicRequest()
    @Serializable
    data class GetUserData(override val code: Int = ProtocolCode.GET_USER_DATA, val userId: ID): MusicRequest()
    @Serializable
    data class GetCurrUserData(override val code: Int = ProtocolCode.GET_CURR_USER_DATA): MusicRequest()
    @Serializable
    data class GetCurrUserPlaylists(override val code: Int = ProtocolCode.GET_CURR_USER_PLAYLISTS): MusicRequest()
    @Serializable
    data class AddSongToFavorites(override val code: Int = ProtocolCode.ADD_SONG_TO_FAVORITES, val songId: ID): MusicRequest()
    @Serializable
    data class DeleteSongFromFavorites(override val code: Int = ProtocolCode.DELETE_SONG_FROM_FAVORITES, val songId: ID): MusicRequest()
}
