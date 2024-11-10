package org.emily.auth.domain.repository

import org.emily.auth.domain.authentication.AuthResult

interface AuthRepository {
    suspend fun signUp(username: String, password: String): AuthResult<Unit>
    suspend fun signIn(username: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}