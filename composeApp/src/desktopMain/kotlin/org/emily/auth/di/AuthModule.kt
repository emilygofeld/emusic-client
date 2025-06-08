package org.emily.auth.di

import org.emily.auth.data.repository.AuthRepositoryImpl
import org.emily.auth.domain.api.AuthApi
import org.emily.auth.domain.repository.AuthRepository
import org.emily.auth.domain.security.EncryptionService
import org.emily.auth.domain.token.TokenService
import org.emily.auth.presentation.auth.viewmodel.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            get<AuthApi>(),
            get<TokenService<String>>(),
            get<EncryptionService>()
        )
    }

    viewModel {
        AuthViewModel(
            repository = get<AuthRepository>()
        )
    }
}
