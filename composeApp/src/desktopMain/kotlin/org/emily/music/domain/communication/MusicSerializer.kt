package org.emily.music.domain.communication

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.emily.core.constants.ProtocolCode

object ResponseDeserializer: JsonContentPolymorphicSerializer<MusicResponse>(MusicResponse::class) {
    public override fun selectDeserializer(element: JsonElement): DeserializationStrategy<MusicResponse> {
        return when (val code = element.jsonObject["code"]?.jsonPrimitive?.intOrNull) {
            ProtocolCode.SUCCESS -> MusicResponse.SuccessResponse.serializer()
            ProtocolCode.ERROR -> MusicResponse.ErrorResponse.serializer()
            ProtocolCode.GET_SONG -> MusicResponse.GetSong.serializer()
            ProtocolCode.GET_PLAYLIST -> MusicResponse.GetPlaylist.serializer()
            ProtocolCode.GET_USER_DATA -> MusicResponse.GetUserData.serializer()
            ProtocolCode.GET_USER_PLAYLISTS -> MusicResponse.GetUserPlaylists.serializer()
            else -> throw SerializationException("Unknown request code: $code")
        }
    }
}