package com.example.testcompose

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.testcompose.ui.theme.TestComposeTheme
import kotlin.concurrent.timer

@Composable
fun CountDownTimer(
    modifier: Modifier = Modifier,
    timeInMillis: Long = 10_000L,
    period: Long = 1_000L
) {

    var count by remember{ mutableStateOf(timeInMillis / period ) }

    timer("timer", true, 0, period) {
        count--
        if (count < 0) {
            cancel()
        }
    }

    Log.d("TIMER", "$count")

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
        Text(
            text = "${count}",
            fontSize = 34.sp

        )
    }
}

@Preview
@Composable
fun PreviewCountDownTimer() {
    TestComposeTheme() {
        Surface {
            CountDownTimer(Modifier.fillMaxSize())
        }
    }
}