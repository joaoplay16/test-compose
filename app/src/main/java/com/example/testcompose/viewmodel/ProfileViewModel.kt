package com.example.testcompose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.testcompose.presentation.ProfileDetails
import com.example.testcompose.presentation.ProfileState

class ProfileViewModel: ViewModel() {

    var state by mutableStateOf(ProfileState(details = ProfileDetails.Remote(false)))

    fun toggleLoading(){
        state = state.copy(isLoading = !state.isLoading)
    }

    fun toggleFollow(){
        (state.details as? ProfileDetails.Remote)?.let { details ->
            state = state.copy(details = details.copy(isFollowing = !details.isFollowing))
        }
    }
}