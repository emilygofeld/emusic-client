package org.emily.music.presentation.playlist.viewmodel

import org.emily.music.domain.models.Playlist
import org.emily.music.domain.models.Song

data class PlaylistState(
    val playlist: Playlist,
    val searchSongBarText: String = "",
    val searchedSongs: List<Song> = emptyList()
)
