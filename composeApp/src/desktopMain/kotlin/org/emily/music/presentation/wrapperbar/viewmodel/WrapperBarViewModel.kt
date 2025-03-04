package org.emily.music.presentation.wrapperbar.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.emily.core.Screen
import org.emily.core.utils.UiEvent
import org.emily.music.domain.communication.MusicResponse
import org.emily.music.domain.repository.MusicRepository
import org.emily.music.presentation.playingsong.PlayingSongState
import org.emily.music.presentation.playingsong.QueueStateManager

class WrapperBarViewModel(
    private val musicRepository: MusicRepository
): ViewModel() {
    var state by mutableStateOf(WrapperBarState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        state = state.copy(
            currentPlayingSong = state.songQueue.firstOrNull(),
            songQueue = state.getSongQueue(),
            historyStack = state.getHistoryStack()
        )
    }


    fun onEvent(event: WrapperBarEvent) {
        when (event) {
            WrapperBarEvent.OnPlayingStateChange ->
                state = state.copy(isPlaying = !state.isPlaying)

            is WrapperBarEvent.OnSearchBarChange -> {
                state = state.copy(searchBarText = event.searchBarText)
                getSearchResults()
            }

            WrapperBarEvent.OnSkipSong ->
                skipSong()

            WrapperBarEvent.OnPlayPreviousSong ->
                playPreviousSong()

            is WrapperBarEvent.OnSongBarChange -> {
                val updatedQueue = state.getSongQueue().apply { addLast(event.newSong) }
                state = state.copy(songQueue = updatedQueue.toList())
            }

            WrapperBarEvent.OnOpenHome ->
                openHomeScreen()
        }
    }


    private fun skipSong() {
        val currentSong = PlayingSongState.currentPlayingSong
        val updatedHistory = state.getHistoryStack().apply {
            currentSong.value?.let { addLast(it) }
        }

        val nextSong = QueueStateManager.removeFromQueueBeginning()
        if (nextSong == null)
            PlayingSongState.stop()
        else
            PlayingSongState.togglePlayingState(nextSong)

        state = state.copy(
            historyStack = updatedHistory.toList(),
            currentPlayingSong = nextSong
        )
    }

    private fun playPreviousSong() {
        val previousSong = state.getHistoryStack().removeLastOrNull()
        val updatedQueue = state.getSongQueue().apply {
            if (previousSong != null) {
                addFirst(previousSong)
            }
        }

        if (previousSong == null)
            PlayingSongState.stop()
        else
            PlayingSongState.togglePlayingState(previousSong)

        state = state.copy(
            songQueue = updatedQueue.toList(),
            currentPlayingSong = previousSong
        )
    }


    private fun openHomeScreen() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate(to = Screen.Home))
        }
    }

    private fun getSearchResults() {
        viewModelScope.launch {
            val musicResponse = musicRepository.getSearchResults(state.searchBarText)

            if (musicResponse is MusicResponse.ErrorResponse)
                println(musicResponse.message)

            else if (musicResponse is MusicResponse.GetSearchResult) {
                val songs = musicResponse.songs
                _uiEvent.send(UiEvent.Navigate(to = Screen.SearchScreen(songs)))
            }
        }
    }
}