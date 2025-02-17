package org.emily.music.presentation.playlist.viewmodel

import PlayingSongState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.emily.core.constants.ID
import org.emily.music.domain.communication.MusicResponse
import org.emily.music.domain.models.Playlist
import org.emily.music.domain.models.Song
import org.emily.music.domain.repository.MusicRepository

class PlaylistViewModel(
    private val musicRepository: MusicRepository,
    private val playlist: Playlist
): ViewModel() {

    var state by mutableStateOf(PlaylistState(playlist))
        private set

    init {
        refreshPlaylist()
    }

    fun onEvent(event: PlaylistEvent) {
        when (event) {
            is PlaylistEvent.OnChangeTitle -> {
                state = state.copy(playlist = playlist.copy(title = event.title))
            }

            is PlaylistEvent.OnAddSong ->
                addSongToPlaylist(event.playlistId, event.songId)

            is PlaylistEvent.OnFavoriteChange ->
                changeFavoriteStatus(event.song)

            is PlaylistEvent.OnDeleteSong ->
                deleteSong(event.playlistId, event.songId)
        }
    }

    private fun changeFavoriteStatus(song: Song) {
        viewModelScope.launch {
            song.isFavorite = !song.isFavorite

            val response = if (song.isFavorite)
                musicRepository.addSongToUserFavorites(song.id)
            else
                musicRepository.removeSongFromUserFavorites(song.id)

            if (response is MusicResponse.ErrorResponse)
                println(response.message)

            refreshPlaylist()
        }
    }

    private fun addSongToPlaylist(playlistId: ID, songId: ID) {
        viewModelScope.launch {
            val addSongResponse = musicRepository.addSongToPlaylist(songId, playlistId)
            if (addSongResponse is MusicResponse.ErrorResponse)
                println(addSongResponse.message)

            refreshPlaylist()
        }
    }

    private fun refreshPlaylist() {
        viewModelScope.launch {
            val response = musicRepository.getPlaylist(state.playlist.id)

            if (response is MusicResponse.GetPlaylist) {
                state = state.copy(playlist = response.playlist)
            }

            else if (response is MusicResponse.ErrorResponse)
                println(response.message)
        }
    }

    private fun deleteSong(playlistId: ID, songId: ID) {
        viewModelScope.launch {
            val deleteSongResponse = musicRepository.removeSongFromPlaylist(songId, playlistId)
            if (deleteSongResponse is MusicResponse.ErrorResponse)
                println(deleteSongResponse.message)

            refreshPlaylist()
        }
    }
}