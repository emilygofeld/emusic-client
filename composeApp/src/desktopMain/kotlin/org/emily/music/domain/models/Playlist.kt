package org.emily.music.domain.models

import kotlinx.serialization.Serializable
import org.emily.core.constants.ID

@Serializable
data class Playlist(
    val title: String,
    val songs: List<Song> = emptyList(),
    val ownerName: String,
    val ownerId: ID,
    val id: ID = ""
)
