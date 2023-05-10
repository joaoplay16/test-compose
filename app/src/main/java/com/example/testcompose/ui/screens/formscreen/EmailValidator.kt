package com.example.testcompose.ui.screens.formscreen

import com.example.testcompose.R
import java.util.regex.Pattern

class EmailValidator : Validator {

    override val errorMessage = R.string.invalid_email

    companion object{
        private const val EMAIL_VALIDATION_REGEX = "^(.+)@(.+)\$"
    }

    override fun isValid(text: String): Boolean {
        return Pattern.matches(EMAIL_VALIDATION_REGEX, text)
    }
}