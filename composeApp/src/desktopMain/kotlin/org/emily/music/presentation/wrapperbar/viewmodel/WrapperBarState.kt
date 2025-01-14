package org.emily.music.presentation.wrapperbar.viewmodel

import org.emily.music.domain.models.PlayingSong

data class WrapperBarState(
    val songQueue: ArrayDeque<PlayingSong> = ArrayDeque(),
    val searchBarText: String = "",
    val isPlaying: Boolean = false,
    val historyStack: ArrayDeque<PlayingSong> = ArrayDeque(),
    val currentPlayingSong: PlayingSong? = null
)
