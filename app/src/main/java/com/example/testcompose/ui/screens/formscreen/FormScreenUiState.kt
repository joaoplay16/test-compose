package com.example.testcompose.ui.screens.formscreen

data class FormScreenUiState (
    val email: EmailState = EmailState(),
    val password: PasswordState = PasswordState()
)