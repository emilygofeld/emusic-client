package org.emily.music.presentation.playingsong

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.emily.music.data.playaudio.playAudio
import org.emily.music.domain.models.Song
import org.emily.music.domain.playaudio.AudioPlayer

object PlayingSongState {
    private val _currentPlayingSong = MutableStateFlow<Song?>(null)
    val currentPlayingSong: StateFlow<Song?> = _currentPlayingSong

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private var audioPlayer: AudioPlayer? = null

    // function plays new song or resumes current playing song
    fun playSong(song: Song) {
        if (_currentPlayingSong.value?.url != song.url) {
            _currentPlayingSong.value = song
            audioPlayer?.close()

            val newAudioPlayer = playAudio(song.url)
            audioPlayer = newAudioPlayer
            _isPlaying.value = true
        } else {
            audioPlayer?.resume()
            _isPlaying.value = true
        }

        QueueStateManager.addSongToRecentSongs(song)
    }

    // function plays new song or pauses/resumes current playing song
    fun togglePlayingState(song: Song? = null) {
        if (_isPlaying.value && (currentPlayingSong.value == song || song == null)) {
            pause()
        } else {
            song?.let {
                playSong(it)
            } ?: _currentPlayingSong.value?.let {
                playSong(it)
            }
        }
    }

    private fun pause() {
        audioPlayer?.pause()
        _isPlaying.value = false
    }

    fun stop() {
        audioPlayer?.close()
        _currentPlayingSong.value = null
    }
}
