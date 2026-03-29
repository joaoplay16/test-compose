package com.example.testcompose.presentation.model

sealed class Gender {
    object Male : Gender()
    object Female: Gender()
}