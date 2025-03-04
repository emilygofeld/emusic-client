package org.emily.music.domain.playaudio

import com.russhwolf.settings.Settings

class StorageService(private val settings: Settings) {
    fun putString(key: String, value: String) {
        settings.putString(key, value)
    }

    fun putInt(key: String, value: Int) {
        settings.putInt(key, value)
    }

    fun getStringOrNull(key: String): String? {
        return settings.getStringOrNull(key)
    }

    fun getInt(key: String): Int {
        return settings.getIntOrNull(key) ?: 0
    }
}