package org.emily.auth.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import org.emily.auth.domain.authentication.AuthRequest
import org.emily.auth.domain.authentication.AuthResponse
import org.emily.auth.domain.authentication.AuthResult
import org.emily.auth.domain.api.AuthApi

class AuthApiImpl(
    private val client: HttpClient
): AuthApi {
    private val baseUrl = "http://10.0.0.24:8080/"

    override suspend fun signUp(request: AuthRequest): AuthResponse {
        return client.post("${baseUrl}signUp") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body() // deserializing the response to AuthResponse
    }

    override suspend fun signIn(request: AuthRequest): AuthResponse {
        return client.post("${baseUrl}signIn") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body() // deserializing the response to AuthResponse
    }

    override suspend fun authenticate(token: String): AuthResult<Unit> {
        val response: HttpResponse = client.get("${baseUrl}auth") {
            header(HttpHeaders.Authorization, token)
        }

        return if (response.status == HttpStatusCode.OK) {
            AuthResult.Authorized()
        } else {
            AuthResult.Unauthorized()
        }
    }
}
