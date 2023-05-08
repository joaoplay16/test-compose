package com.example.testcompose.ui.screens.formscreen

sealed class FormScreenUiEvent {
    data class onEmailChanged(val email: EmailState) : FormScreenUiEvent()
    data class onPasswordChanged(val password: PasswordState) : FormScreenUiEvent()
    object Submit: FormScreenUiEvent()
}