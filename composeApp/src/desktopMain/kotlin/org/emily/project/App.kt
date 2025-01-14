package org.emily.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.emily.music.presentation.wrapperbar.component.BottomBar
import org.emily.music.presentation.wrapperbar.component.SearchBar
import org.emily.music.presentation.wrapperbar.viewmodel.WrapperBarViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    MaterialTheme {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBar(koinViewModel<WrapperBarViewModel>(), modifier = Modifier.align(Alignment.TopCenter))
            BottomBar(koinViewModel<WrapperBarViewModel>(), modifier = Modifier.align(Alignment.BottomCenter))
        }
    

//        var state by remember { mutableStateOf<Screen>(Screen.Splash) }
//        when (state) {
//            Screen.Splash -> {
//                SplashScreen(
//                    vm = koinViewModel<AuthViewModel>(),
//                    onNavigate = { screen ->
//                        state = screen
//                    }
//                )
//            }
//
//            Screen.Login -> {
//                SignInScreen(
//                    vm = koinViewModel<AuthViewModel>(),
//                    onNavigate = { screen ->
//                        state = screen
//                    }
//                )
//            }
//            Screen.Signup -> {
//                SignUpScreen(
//                    vm = koinViewModel<AuthViewModel>(),
//                    onNavigate = { screen ->
//                        state = screen
//                    }
//                )
//            }
//            Screen.Home -> {
//                Box(modifier = Modifier.fillMaxSize())
//            }
//        }
    }
}