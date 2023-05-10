package com.example.testcompose.ui.screens.formscreen

import com.example.testcompose.R

class EmptyValidator : Validator {

    override val errorMessage = R.string.empty_field

    override fun isValid(text: String): Boolean{
        return text.isNotBlank()
    }
}