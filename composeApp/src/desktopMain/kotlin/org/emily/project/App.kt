package org.emily.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
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
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        var state by remember { mutableStateOf<Screen>(Screen.Splash) }
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
                Box(modifier = Modifier.fillMaxSize())
            }
        }
    }
}