package org.emily.auth.domain.authentication

// sealed class to manage authentication result
sealed class AuthResult<T>(val data: T? = null) {
    class Authorized<T>(data: T? = null) : AuthResult<T>(data)
    class Unauthorized<T> : AuthResult<T>()
    data class UnknownError(val message: String? = null) : AuthResult<Unit>()
}