package org.emily.music.data.playaudio

import javazoom.jl.decoder.JavaLayerException
import javazoom.jl.player.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.emily.music.domain.playaudio.AudioPlayer
import java.io.InputStream

class AudioPlayerImpl(
    private val inputStream: InputStream
): AudioPlayer {

    private var player: Player? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var playbackJob: Job? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying

    private val _isPaused = MutableStateFlow(false)
    val isPaused = _isPaused


    override fun play() {
        if (isPlaying.value) return

        _isPlaying.value = true
        _isPaused.value = false
        player = Player(inputStream)

        playbackJob = coroutineScope.launch {
            try {
                while (isPlaying.value) {
                    if (!isPaused.value) {
                        if (!player!!.play(1)) break
                    } else {
                        delay(100)
                    }
                }
                close()
            } catch (e: JavaLayerException) {
                e.printStackTrace()
            }
        }
    }

    override fun pause() {
        if (isPlaying.value && !_isPaused.value) {
            _isPaused.value = true
        }
    }

    override fun resume() {
        if (isPlaying.value && _isPaused.value) {
            _isPaused.value = false
        }
    }

    override fun close() {
        _isPlaying.value = false
        _isPaused.value = false
        playbackJob?.cancel()
        player?.close()
    }
}