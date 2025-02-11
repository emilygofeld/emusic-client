package org.emily.music.domain.playaudio

interface AudioPlayer {
    fun play()
    fun pause()
    fun resume()
    fun close()
}