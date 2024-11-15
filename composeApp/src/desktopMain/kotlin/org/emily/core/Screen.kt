package org.emily.core

sealed class Screen(val route: String) {
    // auth
    data object Login: Screen(route = "Login")
    data object Signup: Screen(route = "Signup")

    // spotify
    data object Home: Screen(route = "home")
}