package com.example.testcompose.presentation

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class FlowViewModel : ViewModel() {

    var _value = mutableStateOf(0)
    val value = _value

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            for (i in 0..50) {
                delay(1000)
                _value.value = i
            }
        }
    }
}