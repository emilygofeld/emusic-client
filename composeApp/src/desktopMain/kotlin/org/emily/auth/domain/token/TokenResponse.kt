package org.emily.auth.domain.token

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val data: String
)
