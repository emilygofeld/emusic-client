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
import org.emily.music.domain.communication.ResponseDeserializer

class MusicApiImpl(
    private val client: HttpClient,
    private val serverIp: String,
) : MusicApi {

    override suspend fun sendApiRequest(request: MusicRequest, token: String): MusicResponse {
        val json = Json {
            encodeDefaults = true
        }

        val jsonEncoded = when (request) {
            is MusicRequest.AddSongToPlaylist -> json.encodeToString(request)
            is MusicRequest.CreatePlaylist -> json.encodeToString(request)
            is MusicRequest.DeletePlaylist -> json.encodeToString(request)
            is MusicRequest.DeleteSongFromPlaylist -> json.encodeToString(request)
            is MusicRequest.GetCurrUserData -> json.encodeToString(request)
            is MusicRequest.GetCurrUserPlaylists -> json.encodeToString(request)
            is MusicRequest.GetPlaylist -> json.encodeToString(request)
            is MusicRequest.GetSong -> json.encodeToString(request)
            is MusicRequest.GetUserData -> json.encodeToString(request)
            is MusicRequest.GetUserPlaylists -> json.encodeToString(request)
        }

        val res = client.post("${serverIp}emusic") {
            contentType(ContentType.Application.Json)
            setBody(jsonEncoded)
            header(HttpHeaders.Authorization, "bearer $token")
        }.bodyAsText()

        return json.decodeFromString(ResponseDeserializer, res)

    }
}
