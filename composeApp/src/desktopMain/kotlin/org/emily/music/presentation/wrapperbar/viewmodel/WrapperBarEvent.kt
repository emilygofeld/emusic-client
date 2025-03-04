package org.emily.music.presentation.wrapperbar.viewmodel

import org.emily.music.domain.models.Song

sealed class WrapperBarEvent {
    data class OnSearchBarChange(val searchBarText: String): WrapperBarEvent()
    data object OnPlayingStateChange: WrapperBarEvent()
    data object OnSkipSong: WrapperBarEvent()
    data object OnPlayPreviousSong: WrapperBarEvent()
    data class OnSongBarChange(val newSong: Song): WrapperBarEvent()
    data object OnOpenHome: WrapperBarEvent()
//    data object OnLogOut: WrapperBarEvent()
}