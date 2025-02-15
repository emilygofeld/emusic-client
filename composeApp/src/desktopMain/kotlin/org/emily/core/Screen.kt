package org.emily.core

import org.emily.music.domain.models.Playlist
import org.emily.music.domain.models.Song

sealed class Screen {
    // auth
    data object Splash: Screen()
    data object Login: Screen()
    data object Signup: Screen()

    // music
    data object Home: Screen()
    data class PlaylistScreen(val playlist: Playlist): Screen()
    data class SearchScreen(val songs: List<Song>): Screen()
}