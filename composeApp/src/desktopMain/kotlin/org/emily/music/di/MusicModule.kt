package org.emily.music.di

import org.emily.auth.domain.token.TokenService
import org.emily.music.data.repository.MusicRepositoryImpl
import org.emily.music.domain.api.MusicApi
import org.emily.music.domain.repository.MusicRepository
import org.koin.dsl.module

val musicModule = module {
    single<MusicRepository> { MusicRepositoryImpl(get<MusicApi>(), get<TokenService<String>>()) }
}