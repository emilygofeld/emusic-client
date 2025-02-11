package org.emily.music.di

import org.emily.auth.domain.token.TokenService
import org.emily.music.data.playaudio.AudioPlayerImpl
import org.emily.music.data.repository.MusicRepositoryImpl
import org.emily.music.domain.api.MusicApi
import org.emily.music.domain.models.Playlist
import org.emily.music.domain.playaudio.AudioPlayer
import org.emily.music.domain.repository.MusicRepository
import org.emily.music.presentation.home.viewmodel.HomeViewModel
import org.emily.music.presentation.playlist.viewmodel.PlaylistViewModel
import org.emily.music.presentation.wrapperbar.viewmodel.WrapperBarViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import java.io.InputStream

val musicModule = module {
    single<MusicRepository> { MusicRepositoryImpl(get<MusicApi>(), get<TokenService<String>>()) }

    viewModel {
        WrapperBarViewModel(
            musicRepository = get<MusicRepository>()
        )
    }

    viewModel {
        HomeViewModel(
            musicRepository = get<MusicRepository>(),
        )
    }

    factory { (playlist: Playlist) ->
        PlaylistViewModel(
            musicRepository = get<MusicRepository>(),
            playlist = playlist
        )
    }

    factory<AudioPlayer> { (inputStream: InputStream) ->
        AudioPlayerImpl(inputStream)
    }
}