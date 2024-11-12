package org.emily.auth.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import org.emily.auth.presentation.UiEvent
import org.emily.auth.presentation.auth.viewmodel.AuthEvent
import org.emily.auth.presentation.auth.viewmodel.AuthViewModel

@Composable
fun SignUpScreen (
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
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    )  { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = state.signUpUsername,
                onValueChange = {
                    vm.onEvent(AuthEvent.SignUpUsernameChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Enter username...")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.signUpPassword,
                onValueChange = {
                    vm.onEvent(AuthEvent.SignUpPasswordChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Enter password...")
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                vm.onEvent(AuthEvent.SignUp)
            }
        ) {
            Text(text = "Sign up")
        }
    }
}