package org.emily.music.presentation.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.emily.core.Screen
import org.emily.core.constants.ID
import org.emily.core.utils.UiEvent
import org.emily.music.domain.communication.MusicResponse
import org.emily.music.domain.models.Playlist
import org.emily.music.domain.repository.MusicRepository

class HomeViewModel(
    private val musicRepository: MusicRepository
): ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getUserPlaylists()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnClickPlaylist ->
                openPlaylist(event.playlist)

            is HomeEvent.OnCreatePlaylist ->
                createPlaylist(event.title)

            is HomeEvent.OnDeletePlaylist ->
                deletePlaylist(event.id)
        }
    }

    private fun openPlaylist(playlist: Playlist) {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate(to = Screen.PlaylistScreen(playlist)))
        }
    }

    private fun getUserPlaylists() {
        viewModelScope.launch {
            val musicResponse = musicRepository.getCurrentUserPlaylists()

            if (musicResponse is MusicResponse.ErrorResponse)
                println(musicResponse.message)

            else if (musicResponse is MusicResponse.GetUserPlaylists)
                state = state.copy(recentPlaylists = musicResponse.playlists)
        }
    }

    private fun createPlaylist(title: String) {
        viewModelScope.launch {
            val musicResponse =  musicRepository.createPlaylistForUser(title)

            if (musicResponse is MusicResponse.ErrorResponse)
                println(musicResponse.message)

            else if (musicResponse is MusicResponse.SuccessResponse)
                getUserPlaylists()
        }
    }

    private fun deletePlaylist(id: ID) {
        viewModelScope.launch {
            val musicResponse =  musicRepository.removePlaylistFromUser(id)

            if (musicResponse is MusicResponse.ErrorResponse)
                println(musicResponse.message)

            else if (musicResponse is MusicResponse.SuccessResponse)
                getUserPlaylists()
        }
    }
}