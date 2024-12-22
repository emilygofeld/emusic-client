package org.emily.music.domain.models

import kotlinx.serialization.Serializable
import org.emily.core.constants.ID

@Serializable
data class Song(
    val title: String,
    val artists: List<String>,
    val length: Int,
    val id: ID = ""
)
