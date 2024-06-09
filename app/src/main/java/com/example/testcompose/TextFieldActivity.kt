package com.example.testcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldDecorator
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.TrendingFlat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun TextFieldActivityContent() {
    Column(Modifier.fillMaxSize()) {
        val state = rememberTextFieldState()

        LaunchedEffect(key1 = state.selection) {
            state.edit {
                Log.d(
                    "TEXTFIELD",
                    "hasSelection: $hasSelection"
                )
            }
        }

        BasicTextField(
            modifier = Modifier
                .border(
                    color = Color.Black.copy(0.7f),
                    width = 2.dp,
                    shape = RoundedCornerShape(40.dp)
                )
                .fillMaxWidth(),
            state = state,
            textStyle = TextStyle(fontSize = 32.sp),
            cursorBrush = SolidColor(Color.Magenta),
            decorator = TextFieldDecorator { textFieldDecorator ->
                TextFieldDefaults.DecorationBox(
                    value = state.text.toString(),
                    innerTextField = { textFieldDecorator() },
                    enabled = true,
                    singleLine = false,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = remember<MutableInteractionSource> { MutableInteractionSource() },
                    isError = false,
                    label = null,
                    placeholder = null,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.Chat,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(8.dp)
                                .size(32.dp)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.TrendingFlat,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(8.dp)
                                .size(32.dp)
                        )
                    },
                )
            },
            inputTransformation = CustomInputTransformation
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.text.toString(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Chat,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(32.dp)
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.TrendingFlat,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(32.dp)
                )
            },

            onValueChange = {}
        )
    }
}

@ExperimentalFoundationApi
object CustomInputTransformation : InputTransformation {
    override fun TextFieldBuffer.transformInput() {
        if (!"Android".contains(this.toString())) {
            this.revertAllChanges()
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