package org.emily.auth.domain.api

import org.emily.auth.domain.authentication.AuthRequest
import org.emily.auth.domain.authentication.AuthResponse
import org.emily.auth.domain.authentication.AuthResult

interface AuthApi {
    suspend fun signUp(request: AuthRequest): AuthResponse
    suspend fun signIn(request: AuthRequest): AuthResponse
    suspend fun authenticate(token: String): AuthResult<Unit>
}