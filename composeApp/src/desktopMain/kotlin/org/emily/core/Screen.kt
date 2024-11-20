package org.emily.core

sealed class Screen {
    // auth
    data object Splash: Screen()
    data object Login: Screen()
    data object Signup: Screen()

    // spotify
    data object Home: Screen()

}