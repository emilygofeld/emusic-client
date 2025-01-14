package org.emily.music.presentation.wrapperbar.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.emily.music.domain.models.PlayingSong
import org.emily.music.domain.models.Song
import org.emily.music.domain.repository.MusicRepository

class WrapperBarViewModel(
    private val musicRepository: MusicRepository
): ViewModel() {
    var state by mutableStateOf(WrapperBarState())
        private set

    init {

        // for testing purpose - delete later

        state.songQueue.add(
            PlayingSong(
                Song("Vampire", artists = listOf("Olivia rodrigo"), length = 5, id = "5")
            )
        )

        state.songQueue.add(
            PlayingSong(
                Song("Options", artists = listOf("NF"), length = 6, id = "4")
            )
        )

        state.songQueue.add(
            PlayingSong(
                Song("Genius", artists = listOf("Sia", "Labrinth", "Diplo"), length = 6, id = "4")
            )
        )

        state = state.copy(currentPlayingSong = state.songQueue.firstOrNull())
    }

    fun onEvent(event: WrapperBarEvent) {
        when (event) {
            WrapperBarEvent.OnPlayingStateChange ->
                state = state.copy(isPlaying = !state.isPlaying)

            is WrapperBarEvent.OnSearchBarChange ->
                state = state.copy(searchBarText = event.searchBarText)

            WrapperBarEvent.OnSkipSong ->
                skipSong()

            WrapperBarEvent.OnPlayPreviousSong ->
                playPreviousSong()

            is WrapperBarEvent.OnSongBarChange ->
                state.songQueue.addLast(event.newSong)
        }
    }

    private fun skipSong() {
        val currentSong = state.songQueue.removeFirstOrNull() ?: return
        state.historyStack.addLast(currentSong)

        val nextSong = state.songQueue.firstOrNull()
        state = state.copy(currentPlayingSong = nextSong)
    }

    private fun playPreviousSong() {
        val previousSong = state.historyStack.removeLastOrNull() ?: return
        state.songQueue.addFirst(previousSong)
        state = state.copy(currentPlayingSong = previousSong)
    }
}