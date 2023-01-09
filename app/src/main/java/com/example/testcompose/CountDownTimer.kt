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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.testcompose.ui.theme.TestComposeTheme
import com.example.testcompose.util.playSound
import kotlin.concurrent.timer


@Composable
fun CountDownTimer(
    modifier: Modifier = Modifier,
    timeInMillis: Long ,
    period: Long = 1_000L,
    action: () -> Unit = {}
) {

    var count by remember{ mutableStateOf(timeInMillis / period ) }

    LaunchedEffect(key1 = null, block = {
        timer("timer", false, 0, period) {
            count--

            if (count <= 0) this.cancel()

            action()

        }
    })

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
        val context = LocalContext.current
        Surface {
            CountDownTimer(
                modifier = Modifier.fillMaxSize(),
                timeInMillis = 50_000L,
                action = {
                    playSound(context, R.raw.beep)
                    Log.d("Timer", "prod at problem")
                }
            )
        }
    }
}