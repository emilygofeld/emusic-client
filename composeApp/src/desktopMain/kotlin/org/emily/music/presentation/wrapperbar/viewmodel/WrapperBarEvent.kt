package org.emily.music.presentation.wrapperbar.viewmodel

import org.emily.music.domain.models.PlayingSong

sealed class WrapperBarEvent {
    data class OnSearchBarChange(val searchBarText: String): WrapperBarEvent()
    data object OnPlayingStateChange: WrapperBarEvent()
    data object OnSkipSong: WrapperBarEvent()
    data object OnPlayPreviousSong: WrapperBarEvent()
    data class OnSongBarChange(val newSong: PlayingSong): WrapperBarEvent()
    data object OnOpenHome: WrapperBarEvent()
}