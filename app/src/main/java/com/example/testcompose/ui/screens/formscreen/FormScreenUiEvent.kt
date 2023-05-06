package com.example.testcompose.ui.screens.formscreen

sealed class FormScreenUiEvent {
    data class onEmailChanged(val email: String) : FormScreenUiEvent()
    data class onPasswordChanged(val password: String) : FormScreenUiEvent()
    object Submit: FormScreenUiEvent()
}