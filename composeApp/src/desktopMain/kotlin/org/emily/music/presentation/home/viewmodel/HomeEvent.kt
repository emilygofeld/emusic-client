package org.emily.music.presentation.home.viewmodel

import org.emily.core.constants.ID
import org.emily.music.domain.models.Playlist

sealed class HomeEvent {
    data class OnClickPlaylist(val playlist: Playlist): HomeEvent()
    data class OnCreatePlaylist(val title: String): HomeEvent()
    data class OnDeletePlaylist(val id: ID): HomeEvent()
    data class OnEditPlaylistDetails(val playlist: Playlist): HomeEvent()
    data object OnGetGlobalLikedSongs : HomeEvent()
}