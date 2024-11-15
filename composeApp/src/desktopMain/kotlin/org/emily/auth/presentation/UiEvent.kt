package org.emily.auth.presentation

import org.emily.core.Screen

sealed interface UiEvent {
    data class ShowSnackBar(val message: String): UiEvent
    data class Navigate(val to: Screen): UiEvent
}