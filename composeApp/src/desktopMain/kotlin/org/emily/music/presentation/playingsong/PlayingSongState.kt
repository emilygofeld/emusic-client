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
    }

    private fun pause() {
        audioPlayer?.pause()
        _isPlaying.value = false
    }

    fun togglePlayingState(song: Song? = null) {
        if (_isPlaying.value) {
            pause()
        } else {
            song?.let {
                playSong(it)
            } ?: _currentPlayingSong.value?.let {
                playSong(it)
            }
        }
    }
}
