package com.example.testcompose.ui.screens.formscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.testcompose.ui.theme.TestComposeTheme

@Composable
fun FormScreen(
    modifier: Modifier = Modifier,
    state: FormScreenUiState,
    onEvent: (FormScreenUiEvent) -> Unit
) {
    Column(modifier) {
        TextField(
            value = state.email,
            placeholder = {
                Text("Login")
            },
            onValueChange = { onEvent(FormScreenUiEvent.onEmailChanged(it)) }
        )
        TextField(
            value = state.password,
            placeholder = {
                Text("Password")
            },
            onValueChange ={ onEvent(FormScreenUiEvent.onPasswordChanged(it)) }
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