package com.example.testcompose.ui.screens.formscreen

interface Validator {
    val errorMessage: Int
    fun isValid(text: String) : Boolean
}