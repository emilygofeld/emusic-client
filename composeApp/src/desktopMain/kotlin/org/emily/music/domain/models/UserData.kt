package org.emily.music.domain.models

import kotlinx.serialization.Serializable
import org.emily.core.constants.ID

@Serializable
data class UserData(
    val playlists: List<ID> = emptyList(),
    val id: ID
)