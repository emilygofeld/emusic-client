package org.emily.auth.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import org.emily.auth.domain.api.AuthApi
import org.emily.auth.domain.authentication.AuthRequest
import org.emily.auth.domain.authentication.AuthResponse
import org.emily.auth.domain.authentication.AuthResult
import org.emily.auth.domain.security.EncryptionService

class AuthApiImpl(
    private val client: HttpClient,
    private val serverIp: String,
    private val encryptionService: EncryptionService
): AuthApi {

    override suspend fun signUp(request: AuthRequest): AuthResponse {
        val res = client.post("${serverIp}signUp") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(AuthRequest.serializer(), request))
        }.bodyAsText()

        println("res: $res")
        val decrypted = encryptionService.decrypt(res)
        return Json.decodeFromString(AuthResponse.serializer(), decrypted)
    }

    override suspend fun signIn(request: AuthRequest): AuthResponse {
        val res = client.post("${serverIp}signIn") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(AuthRequest.serializer(), request))
        }.bodyAsText()

        println("res: $res")
        val decrypted = encryptionService.decrypt(res)
        return Json.decodeFromString(AuthResponse.serializer(), decrypted)
    }

    override suspend fun authenticate(token: String): AuthResult<Unit> {
        val response: HttpResponse = client.get("${serverIp}authenticate") {
            header(HttpHeaders.Authorization, token)
        }

        return if (response.status == HttpStatusCode.OK) {
            AuthResult.Authorized()
        } else {
            AuthResult.Unauthorized()
        }
    }
}
