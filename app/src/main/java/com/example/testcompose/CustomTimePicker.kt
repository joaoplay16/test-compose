package com.example.testcompose

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcompose.ui.theme.TestComposeTheme

@Composable
fun CustomTimePicker(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.size(width = 250.dp, height = 200.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val scrollState = rememberScrollState()

        var hourScrollPosition by remember { mutableStateOf(0f) }

//        Log.d("SCROLL", "${hourScrollPosition}")

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)

                .padding(horizontal = 20.dp)
        ) {
            (0 .. 24).forEach{ hour ->
                Text(
                    modifier = Modifier .onGloballyPositioned { cordinates ->
                        Log.d("SCROLL", "${cordinates.positionInParent().y}")

                    },
                    text = "$hour"
                )
            }
        }
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
        ) {
            (0 .. 59).forEach{ hour ->
                Text(text = "$hour")
            }
        }
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
        ) {
            (0 .. 59).forEach{ hour ->
                Text(text = "$hour")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCustomTimePicker(){
    TestComposeTheme {
            CustomTimePicker()
    }
}
