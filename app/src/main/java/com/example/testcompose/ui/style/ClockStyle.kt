package com.example.testcompose.ui.style

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ClockStyle(
    val clockWidth: Dp = 100.dp,
    val radius: Dp = 100.dp,
    val normalLineColor: Color =Color.LightGray,
    val fiveStepLineColor: Color =Color.DarkGray,
    val normalLineLength: Dp = 15.dp,
    val fiveStepLineLength: Dp = 25.dp,
    val hourHandColor: Color = Color.Black,
    val minuteHandColor: Color = Color.Black,
    val secondHandColor: Color = Color.Red,
)