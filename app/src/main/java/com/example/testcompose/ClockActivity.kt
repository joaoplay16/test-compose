package com.example.testcompose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testcompose.presentation.model.Clock
import com.example.testcompose.ui.theme.TestComposeTheme
import java.time.LocalTime
import java.util.Timer
import java.util.TimerTask

class ClockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var hour by remember { mutableFloatStateOf(0f) }
                    var minute by remember { mutableFloatStateOf(0f) }
                    var second by remember { mutableFloatStateOf(0f) }

                    DisposableEffect(Unit) {
                        val timer = Timer()
                        timer.schedule(object : TimerTask() {
                            @RequiresApi(Build.VERSION_CODES.O)
                            override fun run() {
                                val now = LocalTime.now()
                                second = now.second + (now.nano / 1_000_000_000f)
                                minute = now.minute + (second / 60f)
                                hour = (now.hour % 12) + (minute / 60f)
                            }
                        }, 0, 100)

                        onDispose {
                            timer.cancel()
                        }
                    }

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Clock(
                            hour = hour,
                            minute = minute,
                            second = second,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .align(Alignment.BottomCenter),
                        )
                    }
                }
            }
        }
    }
}