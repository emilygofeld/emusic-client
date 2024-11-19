package org.emily.auth.di

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.emily.auth.data.api.AuthApiImpl
import org.emily.auth.data.repository.AuthRepositoryImpl
import org.emily.auth.data.token.JwtTokenService
import org.emily.auth.domain.api.AuthApi
import org.emily.auth.domain.repository.AuthRepository
import org.emily.auth.domain.token.TokenService
import org.emily.auth.presentation.auth.viewmodel.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val authModule = module {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = false
                isLenient = true
                prettyPrint = true
            })
        }
    }

    val serverIp = "http://10.0.0.13:8080/"

    single<AuthApi> { AuthApiImpl(client, serverIp) }
    single<TokenService<String>> { JwtTokenService(Settings()) }
    single<AuthRepository> { AuthRepositoryImpl(get<AuthApi>(), get<TokenService<String>>()) }

    viewModel {
        AuthViewModel(
            repository = get<AuthRepository>()
        )
    }

}