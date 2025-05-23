package org.emily.auth.presentation.auth.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.emily.auth.domain.authentication.AuthResult
import org.emily.auth.domain.repository.AuthRepository
import org.emily.core.utils.UiEvent
import org.emily.core.Screen

class AuthViewModel (
    private val repository: AuthRepository
): ViewModel() {
    var state by mutableStateOf(AuthState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        authenticate()
    }

    fun onEvent(event: AuthEvent) {
        when(event) {
            is AuthEvent.SignInUsernameChanged -> {
                state = state.copy(signInUsername = event.value)
            }
            is AuthEvent.SignInPasswordChanged -> {
                state = state.copy(signInPassword = event.value)
            }
            is AuthEvent.SignUpUsernameChanged -> {
                state = state.copy(signUpUsername = event.value)
            }
            is AuthEvent.SignUpPasswordChanged -> {
                state = state.copy(signUpPassword = event.value)
            }
            AuthEvent.SignIn -> {
                signIn()
            }
            AuthEvent.SignUp -> {
                signUp()
            }
            AuthEvent.ToSignUp -> {
                toSignUp()
            }
            AuthEvent.ToSignIn -> {
                toSignIn()
            }
            AuthEvent.Clear -> {
                state = state.copy(signUpPassword = "", signUpUsername = "", signInPassword = "", signInUsername = "")
            }
        }
    }


    private fun signUp() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signUp(
                username = state.signUpUsername,
                password = state.signUpPassword
            )
            if (result is AuthResult.UnknownError) {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(result.message ?: "Unknown Error")
                )
                return@launch
            }
            _uiEvent.send(UiEvent.Navigate(Screen.Home))
            state = state.copy(isLoading = false)
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signIn(
                username = state.signInUsername,
                password = state.signInPassword
            )
            if (result is AuthResult.UnknownError) {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(result.message ?: "Unknown Error")
                )
                return@launch
            }
            _uiEvent.send(UiEvent.Navigate(Screen.Home))
            state = state.copy(isLoading = false)
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when (val result = repository.authenticate()) {
                is AuthResult.Authorized -> _uiEvent.send(UiEvent.Navigate(Screen.Home))
                is AuthResult.Unauthorized -> _uiEvent.send(UiEvent.Navigate(Screen.Login))
                is AuthResult.UnknownError -> _uiEvent.send(UiEvent.ShowSnackBar(result.message ?: "Unknown Error"))
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun toSignIn() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            _uiEvent.send(
                UiEvent.Navigate(to = Screen.Login)
            )
            state = state.copy(isLoading = false)
        }
    }

    private fun toSignUp() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            _uiEvent.send(
                UiEvent.Navigate(to = Screen.Signup)
            )
            state = state.copy(isLoading = false)
        }
    }
}