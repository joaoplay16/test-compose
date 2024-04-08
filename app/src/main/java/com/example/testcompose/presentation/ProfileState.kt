package com.example.testcompose.presentation

data class ProfileState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val details: ProfileDetails = ProfileDetails.Local(false)
)

data class Post(val id: String)

sealed interface ProfileDetails {
    data class Local(val isUpdatingProfilePicture: Boolean): ProfileDetails
    data class Remote(val isFollowing: Boolean): ProfileDetails
}