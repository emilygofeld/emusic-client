package org.emily.auth.domain.authentication
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthResponse {
    @Serializable
    @SerialName("Success")
    data class SuccessResponse(val token: String) : AuthResponse
    @Serializable
    @SerialName("Error")
    data class ErrorResponse(val message: String) : AuthResponse
}