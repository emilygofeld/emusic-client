package org.emily.auth.presentation

sealed interface UiEvent {
    data class ShowSnackBar(val message: String): UiEvent
}