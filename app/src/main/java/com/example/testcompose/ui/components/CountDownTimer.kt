package com.example.testcompose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.testcompose.ui.theme.TestComposeTheme
import com.example.testcompose.util.playSound
import com.example.testcompose.viewmodel.TimeViewModel


@Composable
fun CountDownTimer(
    modifier: Modifier = Modifier,
    count: String = "0",
    start: () -> Unit,
    stop: () -> Unit,
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = count, fontSize = 48.sp)

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround){
            Button(onClick = {
                start()
            }) {
                Text(text = "Start", fontSize = 48.sp)
            }
            Button(onClick = {
                stop()
            }) {
                Text(text = "Stop", fontSize = 48.sp)
            }
        }
    }
}

@Preview
@Composable
fun PreviewCountDownTimer() {
    val viewModel = TimeViewModel()
    viewModel.setTimeValue(30)
    val count by viewModel.timerValue

    TestComposeTheme() {
        val context = LocalContext.current
        Surface {
            CountDownTimer(
                modifier = Modifier.fillMaxSize(),
                count = count.toString(),
                start = {
                    viewModel.startTimer(action = {
                        playSound(context, R.raw.beep)
                    })
                },
                stop = {
                    viewModel.stopTimer()
                },
            )
        }
    }
}