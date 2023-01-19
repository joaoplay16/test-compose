package com.example.testcompose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

class TimerViewModel : ViewModel() {
    private var timer: Timer? = null

    private var _timerValue: MutableState<Long> = mutableStateOf(0)

    val timerValue = _timerValue

    fun setTimeValue(timeInMillis: Long){
        _timerValue.value = timeInMillis
    }

    fun startTimer(action: () -> Unit){
        if (timerValue.value != 0L) {
            timer = Timer()
            viewModelScope.launch {
                timer?.scheduleAtFixedRate( object : TimerTask() {
                    override fun run() {
                        _timerValue.value--
                        action()
                        if (hasFinished()) {
                            this.cancel()
                        }
                    }
                }, 0L, 1000L)
            }
        }
    }

    fun hasFinished(): Boolean{
        return timerValue.value == 0L
    }

    fun stopTimer(){
        timer?.cancel()
    }
}