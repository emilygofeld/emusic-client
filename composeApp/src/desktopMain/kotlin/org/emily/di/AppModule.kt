package org.emily.di

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.emily.auth.data.api.AuthApiImpl
import org.emily.auth.data.token.JwtTokenService
import org.emily.auth.domain.api.AuthApi
import org.emily.auth.domain.token.TokenService
import org.emily.music.data.api.MusicApiImpl
import org.emily.music.domain.api.MusicApi
import org.koin.dsl.module

val appModule = module {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = false
                isLenient = true
                prettyPrint = true
            })
        }
    }

    val serverIp = "http://172.20.10.5:8080/"
    single<TokenService<String>> { JwtTokenService(Settings()) }

    // auth
    single<AuthApi> { AuthApiImpl(client, serverIp) }

    // music
    single<MusicApi> { MusicApiImpl(client, serverIp) }
}