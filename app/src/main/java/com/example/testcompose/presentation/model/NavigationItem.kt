package com.example.testcompose.presentation.model

import com.example.testcompose.R

enum class NavigationItem(
    val title: String,
    val icon: Int
) {
    Home(
        icon = R.drawable.baseline_home_filled_24,
        title = "Home"
    ),
    Profile(
        icon = R.drawable.baseline_person_24,
        title = "Profile"
    ),
    Premium(
        icon = R.drawable.baseline_diamond_24,
        title = "Premium"
    ),
    Settings(
        icon = R.drawable.baseline_settings_24,
        title = "Settings"
    )
}