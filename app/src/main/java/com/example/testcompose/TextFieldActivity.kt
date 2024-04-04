package com.example.testcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.TextFieldBuffer
import androidx.compose.foundation.text2.input.TextFieldCharSequence
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.testcompose.ui.theme.TestComposeTheme

class TextFieldActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeTheme {
                Surface(color = MaterialTheme.colorScheme.tertiaryContainer) {
                    TextFieldActivityContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TextFieldActivityContent() {
    Column(Modifier.fillMaxSize()) {
        val state = rememberTextFieldState()

        state.edit {
            Log.d(
                "TEXTFIELD",
                "hasSelection: $hasSelection"
            )
        }

        BasicTextField2(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            state = state,
            textStyle = TextStyle(fontSize = 32.sp),
            inputTransformation = CustomInputTransformation
        )
    }
}

@ExperimentalFoundationApi
object CustomInputTransformation : InputTransformation {
    override fun transformInput(
        originalValue: TextFieldCharSequence,
        valueWithChanges: TextFieldBuffer
    ) {
        if (!"Android".contains(valueWithChanges.asCharSequence())) {
            valueWithChanges.revertAllChanges()
        }
    }
}

@Preview
@Composable
fun TextFieldActivityContentPreview() {
    TestComposeTheme {
        Surface(color = MaterialTheme.colorScheme.tertiaryContainer) {
            TextFieldActivityContent()
        }
    }
}