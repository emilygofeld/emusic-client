package org.emily.music.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.emily.music.domain.api.MusicApi
import org.emily.music.domain.communication.MusicRequest
import org.emily.music.domain.communication.MusicResponse
import kotlin.reflect.KClass
import kotlin.reflect.safeCast

class MusicApiImpl(
    private val client: HttpClient,
    private val serverIp: String,
) : MusicApi {

    override suspend fun <T : MusicRequest> sendApiRequest(
        musicRequest: MusicRequest,
        type: KClass<T>,
        token: String
    ): MusicResponse {
        return sendRequest(musicRequest, type, token)
    }

    private suspend inline fun sendRequest(request: MusicRequest, type: KClass<*>, token: String): MusicResponse {
        val json = Json { encodeDefaults = true }
        val castedRequest = type.safeCast(request) ?: return MusicResponse.ErrorResponse(message = "Server Error")
        val res = client.post("${serverIp}emusic") {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(castedRequest))
            header(HttpHeaders.Authorization, token)
        }.bodyAsText()
        return json.decodeFromString(MusicResponse.serializer(), res)
    }
}
