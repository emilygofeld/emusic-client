package org.emily.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.emily.auth.presentation.auth.viewmodel.AuthViewModel
import org.emily.auth.presentation.screens.SignInScreen
import org.emily.auth.presentation.screens.SignUpScreen
import org.emily.auth.presentation.screens.SplashScreen
import org.emily.core.Screen
import org.emily.music.domain.models.Song
import org.emily.music.presentation.home.HomeScreen
import org.emily.music.presentation.home.viewmodel.HomeViewModel
import org.emily.music.presentation.playlist.component.PlaylistScreen
import org.emily.music.presentation.playlist.viewmodel.PlaylistViewModel
import org.emily.music.presentation.wrapperbar.component.BottomBar
import org.emily.music.presentation.wrapperbar.component.SearchBar
import org.emily.music.presentation.wrapperbar.screen.SearchBarScreen
import org.emily.music.presentation.wrapperbar.viewmodel.WrapperBarViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun App() {
    MaterialTheme {

        // delete later
        val songsForTesting = mutableListOf(
            Song("Casual", artists = listOf("Chappell Roan"), length = 342, id = "5"),
            Song("Guess ft. Billie Eilish", artists = listOf("Charlie XCX", "Billie Eilish"), length = 235, id = "6"),
            Song("Forget Her", artists = listOf("Jeff Buckley"), length = 707, id = "7")
        )

        var state by remember { mutableStateOf<Screen>(Screen.Splash) }

        Scaffold(
            topBar = {
                if (state !is Screen.Signup && state !is Screen.Login && state !is Screen.Splash) {
                    SearchBar(
                        koinViewModel<WrapperBarViewModel>(),
                        onNavigate = { screen ->
                            state = screen
                        }
                    )
                }
            },
            bottomBar = {
                if (state !is Screen.Signup && state !is Screen.Login && state !is Screen.Splash) {
                    BottomBar(
                        koinViewModel<WrapperBarViewModel>(),
                        onNavigate = { screen ->
                            state = screen
                        }
                    )
                }
            }
        ) { padding ->

            SearchBarScreen(songsForTesting)

            Box(modifier = Modifier.padding(padding)) {
                when (state) {
                    Screen.Splash -> {
                        SplashScreen(
                            vm = koinViewModel<AuthViewModel>(),
                            onNavigate = { screen ->
                                state = screen
                            }
                        )
                    }

                    Screen.Login -> {
                        SignInScreen(
                            vm = koinViewModel<AuthViewModel>(),
                            onNavigate = { screen ->
                                state = screen
                            }
                        )
                    }
                    Screen.Signup -> {
                        SignUpScreen(
                            vm = koinViewModel<AuthViewModel>(),
                            onNavigate = { screen ->
                                state = screen
                            }
                        )
                    }
                    Screen.Home -> {
                        HomeScreen(
                            vm = koinViewModel<HomeViewModel>(),
                            onNavigate = { screen ->
                                state = screen
                            }
                        )
                    }

                    is Screen.PlaylistScreen -> {
                        val playlist = (state as Screen.PlaylistScreen).playlist
                        PlaylistScreen(
                            vm = koinInject<PlaylistViewModel>(parameters = { parametersOf(playlist) }),
                            songsForTesting =  songsForTesting
                        )
                    }

                    is Screen.SearchScreen -> {
                        val songs = (state as Screen.SearchScreen).songs
                        SearchBarScreen(songs)
                    }
                }
            }
        }
    }
}