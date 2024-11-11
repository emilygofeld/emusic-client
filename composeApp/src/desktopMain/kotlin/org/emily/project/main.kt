package org.emily.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.emily.auth.di.authModule
import org.koin.core.context.GlobalContext.startKoin

fun main() = application {
    startKoin {
        modules(
            authModule
        )
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "spotify-client",
    ) {
        App()
    }
}