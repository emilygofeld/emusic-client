package org.emily.auth.data.token

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import org.emily.auth.domain.token.TokenService

class JwtTokenService(
    private val settings: Settings
): TokenService<String> {
    override suspend fun put(value: String) {
        settings.putString("token", value)
    }

    override suspend fun get(): String? {
        return settings["token"]
    }

    override suspend fun clear() {
        settings.clear()
    }
}