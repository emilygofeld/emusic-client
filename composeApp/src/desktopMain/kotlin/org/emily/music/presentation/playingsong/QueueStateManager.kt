package org.emily.music.presentation.playingsong

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.emily.music.domain.models.Song
import org.emily.music.domain.playaudio.StorageService
import org.koin.core.context.GlobalContext

object QueueStateManager {
    private const val RECENT_SONGS_KEY = "recent_songs"
    private const val INDEX_KEY = "current_index"
    private const val QUEUE_KEY = "queue_songs"
    private const val MAX_RECENT_SONGS = 20

    private val storageService: StorageService by lazy {
        GlobalContext.get().get<StorageService>()
    }

    fun addSongToRecentSongs(song: Song) {
        val songList = getRecentSongs().toMutableList()

        var currentIndex = storageService.getInt(INDEX_KEY)
        val isSongInRecent = songList.any { recentSong -> recentSong.id == song.id }

        if (!isSongInRecent) {
            if (songList.size < MAX_RECENT_SONGS) {
                songList.add(song)
            } else {
                songList[currentIndex] = song
            }
            currentIndex = (currentIndex + 1) % MAX_RECENT_SONGS
        }

        // update song list & index
        storageService.putString(RECENT_SONGS_KEY, Json.encodeToString(songList))
        storageService.putInt(INDEX_KEY, currentIndex)
    }

    fun getRecentSongs(): List<Song> {
        val songListJson = storageService.getStringOrNull(RECENT_SONGS_KEY)
        return if (songListJson != null) {
            Json.decodeFromString<List<Song>>(songListJson)
        } else {
            emptyList()
        }
    }

    fun addSongToQueue(song: Song) {
        val queue = getQueue()
        queue.addLast(song)

        storageService.putString(QUEUE_KEY, Json.encodeToString(queue.toList()))
    }

    fun removeFromQueueBeginning(): Song? {
        val queue = getQueue()

        val nextSongInQueue = queue.removeFirstOrNull()
        storageService.putString(QUEUE_KEY, Json.encodeToString(queue.toList()))

        return nextSongInQueue
    }

    private fun getQueue(): ArrayDeque<Song> {
        val queueJson = storageService.getStringOrNull(QUEUE_KEY)
        return if (queueJson != null) {
            ArrayDeque(Json.decodeFromString<List<Song>>(queueJson))
        } else {
            ArrayDeque()
        }
    }
}
