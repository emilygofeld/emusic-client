package org.emily.music.domain.api

import org.emily.music.domain.communication.MusicRequest
import org.emily.music.domain.communication.MusicResponse

interface MusicApi {
    suspend fun sendApiRequest(request: MusicRequest, token: String): MusicResponse
}


