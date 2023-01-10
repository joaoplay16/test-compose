package com.example.testcompose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TimeViewModel : ViewModel() {
    private var _timer: MutableState<Long> = mutableStateOf(0)
    val timer = _timer

    fun setTimeValue(timeInMillis: Long){
        _timer.value = timeInMillis
    }
}