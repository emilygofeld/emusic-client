package org.emily.auth.domain.authentication

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val username: String,
    val password: String
)

