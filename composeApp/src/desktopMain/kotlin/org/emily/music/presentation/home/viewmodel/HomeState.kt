package org.emily.music.presentation.home.viewmodel

import org.emily.music.domain.models.Playlist
import org.emily.music.domain.models.Song

data class HomeState(
    val recentSongs: List<Song> = emptyList(),
    val recentPlaylists: List<Playlist> = emptyList()
)