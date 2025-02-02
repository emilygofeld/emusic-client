package org.emily.music.domain.models

import kotlinx.serialization.Serializable
import org.emily.core.constants.ID

@Serializable
class Song(
    val title: String,
    val artists: List<String>,
    val length: Int,
    var isFavorite: Boolean = false,
    val url: String = "",
    val id: ID = ""
)
