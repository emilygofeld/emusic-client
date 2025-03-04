package org.emily.music.presentation.wrapperbar.viewmodel

import kotlinx.serialization.Serializable
import org.emily.music.domain.models.Song

@Serializable
data class WrapperBarState(
    val songQueue: List<Song> = emptyList(),
    val searchBarText: String = "",
    val isPlaying: Boolean = false,
    val historyStack: List<Song> = emptyList(),
    val currentPlayingSong: Song? = null
) {
    fun getSongQueue(): ArrayDeque<Song> = ArrayDeque(songQueue)
    fun getHistoryStack(): ArrayDeque<Song> = ArrayDeque(historyStack)
}
