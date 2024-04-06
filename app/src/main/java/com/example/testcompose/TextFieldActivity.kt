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
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.TextFieldDecorator
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.TextFieldBuffer
import androidx.compose.foundation.text2.input.TextFieldCharSequence
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.TrendingFlat
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
    ExperimentalMaterialApi::class
)
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
                TextFieldDefaults.TextFieldDecorationBox(
                    value = state.text.toString(),
                    visualTransformation = VisualTransformation.None,
                    innerTextField = { textFieldDecorator() },
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
                    enabled = true,
                    singleLine = false,
                    interactionSource = remember { MutableInteractionSource() },
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
    override fun transformInput(
        originalValue: TextFieldCharSequence,
        valueWithChanges: TextFieldBuffer
    ) {
//        if (!"Android".contains(valueWithChanges.asCharSequence())) {
//            valueWithChanges.revertAllChanges()
//        }
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