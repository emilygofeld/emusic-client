package org.emily.music.domain.api

import org.emily.music.domain.communication.MusicRequest
import org.emily.music.domain.communication.MusicResponse
import kotlin.reflect.KClass

interface MusicApi {
    suspend fun<T : MusicRequest> sendApiRequest(
        musicRequest: MusicRequest,
        type: KClass<T>,
        token: String
    ): MusicResponse
}


