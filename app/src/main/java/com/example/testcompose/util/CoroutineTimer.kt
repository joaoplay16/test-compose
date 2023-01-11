package com.example.testcompose.util

import kotlinx.coroutines.*

class CoroutineTimer(
    private val delayMillis: Long = 0,
    val repeatMillis: Long = 0,
    val action: () -> Unit
) {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private fun startCoroutineTimer() = scope.launch(Dispatchers.IO) {
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (true) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }

    private val timer: Job by lazy{  startCoroutineTimer() }

    fun startTimer() {
        timer.start()
    }

    fun cancelTimer() {
        timer.cancel()
    }

}