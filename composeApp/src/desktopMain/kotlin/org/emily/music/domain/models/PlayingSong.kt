package org.emily.music.domain.models

data class PlayingSong(
    val song: Song,
    var currentSecond: Int = 0,
    var isPaused: Boolean = false // delete ?
)
