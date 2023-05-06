package com.example.testcompose.ui.screens.formscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FormViewModel : ViewModel() {

    var state by mutableStateOf(FormScreenUiState())
    private set

    var isSubmitted = MutableStateFlow<Boolean?>(null)
    private set

    fun onEvent(event: FormScreenUiEvent){
        when(event){
            is FormScreenUiEvent.onEmailChanged -> {
                state = state.copy(email = event.email)
            }

            is FormScreenUiEvent.onPasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is FormScreenUiEvent.Submit -> {
                viewModelScope.launch {
                    isSubmitted.emit(true)
                }
            }
        }
    }
}