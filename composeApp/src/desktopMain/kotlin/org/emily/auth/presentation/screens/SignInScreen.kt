package org.emily.auth.presentation.screens

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.collectLatest
import org.emily.auth.presentation.UiEvent
import org.emily.auth.presentation.auth.viewmodel.AuthViewModel

@Composable
fun SignInScreen(
    vm: AuthViewModel
) {
    val state = vm.state
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(vm) {
        vm.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }


}