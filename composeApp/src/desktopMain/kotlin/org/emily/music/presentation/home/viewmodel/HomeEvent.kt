package org.emily.music.presentation.home.viewmodel

import org.emily.music.domain.models.Playlist

sealed class HomeEvent {
    data class OnClickPlaylist(val playlist: Playlist): HomeEvent()
    data class OnCreatePlaylist(val title: String): HomeEvent()
}