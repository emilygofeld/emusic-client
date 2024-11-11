package org.emily.auth.presentation.auth.viewmodel

sealed class AuthEvent {
    data class SignUpUsernameChanged(val value: String): AuthEvent()
    data class SignUpPasswordChanged(val value: String): AuthEvent()
    data object SignUp: AuthEvent()

    data class SignInUsernameChanged(val value: String): AuthEvent()
    data class SignInPasswordChanged(val value: String): AuthEvent()
    data object SignIn: AuthEvent()
}