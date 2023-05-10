package com.example.testcompose.ui.screens.formscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcompose.R
import com.example.testcompose.ui.theme.TestComposeTheme

@Composable
fun FormScreen(
    modifier: Modifier = Modifier,
    state: FormScreenUiState,
    onEvent: (FormScreenUiEvent) -> Unit
) {
    Column(modifier) {
        Email(
            emailState = state.email,
        )
        Spacer(Modifier.padding(8.dp))
        Password(
            passwordState = state.password,
        )
        Spacer(Modifier.padding(8.dp))

        Button(
            onClick = {
                onEvent(FormScreenUiEvent.Submit) },
        ){
            Text(text = "submit")
        }
    }
}

@Composable
fun Email(
    emailState: EmailState,
) {
    TextField(
        modifier = Modifier.onFocusChanged { focusState ->
            emailState.onFocusChange(focusState.isFocused)
            if (!focusState.isFocused) {
                emailState.enableShowErrors()
            }
        },
        value = emailState.text,
        placeholder = {
            Text("Login")
        },
        onValueChange = {
            emailState.text = it
        }
    )

    emailState.getError()?.let { error ->
        TextFieldError(textError = stringResource(id = error))
    }
}

@Composable
fun Password(
    passwordState: PasswordState,
) {
    TextField(
        value = passwordState.text,
        placeholder = {
            Text("Password")
        },
        onValueChange = {
            passwordState.text = it
            passwordState.enableShowErrors()
        }
    )

    passwordState.getError()?.let {
            error -> TextFieldError(textError =  stringResource(id = error))
    }
}

@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        androidx.compose.material3.Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.error
        )
    }
}


@Preview
@Composable
fun FormScreenPreview() {
    TestComposeTheme {
        Surface {
            FormScreen(
                state = FormScreenUiState(),
                onEvent = {}
            )
        }
    }
}