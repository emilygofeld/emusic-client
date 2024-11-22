package org.emily.auth.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import org.emily.auth.presentation.UiEvent
import org.emily.auth.presentation.auth.viewmodel.AuthEvent
import org.emily.auth.presentation.auth.viewmodel.AuthViewModel
import org.emily.core.Screen
import org.emily.project.black
import org.emily.project.primaryColor
import org.emily.project.secondaryColor

@Composable
fun SplashScreen(
    vm: AuthViewModel,
    onNavigate: (to: Screen) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(vm) {
        vm.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
                is UiEvent.Navigate -> {
                    vm.onEvent(AuthEvent.Clear)
                    onNavigate(event.to)
                }
            }
        }
    }
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(primaryColor, black)
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(brush = gradientBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Home, contentDescription = "home", tint = secondaryColor, modifier = Modifier.size(128.dp))
        }
    }
}