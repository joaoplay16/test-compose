package com.example.testcompose.ui.screens.formscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class FormViewModel : ViewModel() {

    var _emailState = mutableStateOf("")
    val emailState = _emailState

    var _passwordState = mutableStateOf("")
    val passwordState = _emailState

    fun onEvent(event: FormScreenUiEvent){
        when(event){
            is FormScreenUiEvent.onEmailChanged -> {
               _emailState.value = event.email
            }

            is FormScreenUiEvent.onPasswordChanged -> {
                _passwordState.value = event.password
            }

            else -> {}
        }
    }
}