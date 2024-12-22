package org.emily.auth.data.repository

import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import org.emily.auth.domain.api.AuthApi
import org.emily.auth.domain.authentication.AuthRequest
import org.emily.auth.domain.authentication.AuthResponse
import org.emily.auth.domain.authentication.AuthResult
import org.emily.auth.domain.repository.AuthRepository
import org.emily.auth.domain.token.TokenService

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val tokenService: TokenService<String>
): AuthRepository {

    override suspend fun signUp(username: String, password: String): AuthResult<Unit> {
        return try {
            when (val response = api.signUp(AuthRequest(username, password))) {
                is AuthResponse.SuccessResponse -> {
                    tokenService.put(response.token) // save the token
                    AuthResult.Authorized(Unit)
                }
                is AuthResponse.ErrorResponse -> {
                    AuthResult.UnknownError(response.message)
                }
            }
        } catch (e: Exception) {
            AuthResult.UnknownError(e.localizedMessage)
        }
    }

    override suspend fun signIn(username: String, password: String): AuthResult<Unit> {
        return try {
            when (val response = api.signIn(AuthRequest(username, password))) {
                is AuthResponse.SuccessResponse -> {
                    tokenService.put(response.token) // save the token
                    AuthResult.Authorized(Unit)
                }
                is AuthResponse.ErrorResponse -> {
                    AuthResult.UnknownError(response.message)
                }
            }
        } catch (e: Exception) {
            AuthResult.UnknownError(e.localizedMessage)
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        println(tokenService.get())
//        tokenService.clear()
        val token = tokenService.get() ?: return AuthResult.Unauthorized()
        return try {
            api.authenticate("Bearer $token")
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.Unauthorized) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError(e.message)
            }
        } catch (e: Exception) {
            AuthResult.UnknownError(e.message)
        }
    }
}