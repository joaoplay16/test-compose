package com.example.testcompose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SampleViewModel : ViewModel() {
    var isLoggedIn by mutableStateOf(false)
        private set

    fun setLogged(isLoggedIn: Boolean){
        this.isLoggedIn = isLoggedIn
    }
}
