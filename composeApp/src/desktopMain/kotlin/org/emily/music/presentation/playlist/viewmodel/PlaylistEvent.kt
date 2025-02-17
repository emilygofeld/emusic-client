package org.emily.music.presentation.playlist.viewmodel

import org.emily.core.constants.ID
import org.emily.music.domain.models.Song

sealed class PlaylistEvent {
    data class OnChangeTitle(val title: String): PlaylistEvent()
    data class OnAddSong(val playlistId: ID, val songId: ID): PlaylistEvent()
    data class OnFavoriteChange(val song: Song): PlaylistEvent()
    data class OnDeleteSong(val playlistId: ID, val songId: ID): PlaylistEvent()
}