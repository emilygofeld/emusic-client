package org.emily.music.data.playaudio

import org.emily.music.domain.playaudio.AudioPlayer
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URI

object AudioPlayerProvider : KoinComponent {
    fun create(inputStream: InputStream): AudioPlayer {
        return get<AudioPlayer> { parametersOf(inputStream) }
    }
}

fun playAudio(url: String): AudioPlayer? {
    return try {
        val uri = URI(url)
        val connection = uri.toURL().openConnection() as HttpURLConnection
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")
        connection.requestMethod = "GET"
        connection.connect()

        if (connection.responseCode != HttpURLConnection.HTTP_OK) {
            println("Failed to stream file. HTTP response code: ${connection.responseCode}")
            return null
        }

        println("started playing")

        val inputStream = BufferedInputStream(connection.inputStream)
        val audioPlayer = AudioPlayerProvider.create(inputStream)

        audioPlayer.play()
        audioPlayer
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

