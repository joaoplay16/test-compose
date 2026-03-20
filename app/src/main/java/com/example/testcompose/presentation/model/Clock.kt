package com.example.testcompose.presentation.model

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcompose.ui.style.ClockStyle
import com.example.testcompose.ui.theme.TestComposeTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Clock(
    modifier: Modifier = Modifier,
    style: ClockStyle = ClockStyle(),
    hour: Float = 0f,
    minute: Float = 0f,
    second: Float = 0f,
) {
    val radius = style.radius
    val scaleWidth = style.clockWidth
    var center by remember {
        mutableStateOf(Offset.Zero)
    }
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    Canvas(
        modifier = modifier
    ) {
        center = this.center
        circleCenter = Offset(center.x, radius.toPx())

        val outerRadius = radius.toPx() + scaleWidth.toPx() / 2f
        val innerRadius = radius.toPx() - scaleWidth.toPx() / 2f

        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                circleCenter.x, circleCenter.y, radius.toPx(), Paint().apply {
                    strokeWidth = scaleWidth.toPx()
                    color = Color.WHITE
                    setStyle(Paint.Style.FILL_AND_STROKE)
                    setShadowLayer(
                        60f, 0f, 0f, Color.argb(50, 0, 0, 0)
                    )
                })
        }

        //Draw lines
        for (i in 1..60) {

            //Math.PI / 180: É aproximadamente 0,0174. Este é o valor de 1 grau em radianos.
            val angleInRad = (i * 6 - 90) * (Math.PI / 180f).toFloat()

            val lineType = if (i % 5 == 0) LineType.FiveStep
            else LineType.Normal


            val lineLength = when (lineType) {
                LineType.Normal -> style.normalLineLength.toPx()
                LineType.FiveStep -> style.fiveStepLineLength.toPx()
                else -> style.normalLineLength.toPx()
            }

            val lineColor = when (lineType) {
                LineType.Normal -> style.normalLineColor
                LineType.FiveStep -> style.fiveStepLineColor
                else -> style.normalLineColor
            }

            val lineStart = Offset(
                x = (outerRadius - lineLength) * cos(angleInRad) + circleCenter.x,
                y = (outerRadius - lineLength) * sin(angleInRad) + circleCenter.y
            )

            val lineEnd = Offset(
                x = outerRadius * cos(angleInRad) + circleCenter.x,
                y = outerRadius * sin(angleInRad) + circleCenter.y
            )

            drawLine(
                color = lineColor, start = lineStart, end = lineEnd, strokeWidth = 1.dp.toPx()
            )
        }

        val oneDegreeInRadian = (Math.PI / 180f).toFloat()

        val secondAngleInRad = (second * 6f - 90f) * oneDegreeInRadian

        val minuteAngleInRad = (minute * 6f - 90f) * oneDegreeInRadian

        val hourAngleInRad = (hour * 30f - 90f) * oneDegreeInRadian

        val hourLineStart = Offset(
            x = (outerRadius - style.fiveStepLineLength.toPx() * 2) * cos(hourAngleInRad) + circleCenter.x,
            y = (outerRadius - style.fiveStepLineLength.toPx() * 2) * sin(hourAngleInRad) + circleCenter.y,
        )

        val minuteLineStart = Offset(
            x = (outerRadius - style.fiveStepLineLength.toPx() * 1.5f) * cos(minuteAngleInRad) + circleCenter.x,
            y = (outerRadius - style.fiveStepLineLength.toPx() * 1.5f) * sin(minuteAngleInRad) + circleCenter.y,
        )

        val secondLineStart = Offset(
            x = (outerRadius - style.fiveStepLineLength.toPx() * 1.5f) * cos(secondAngleInRad) + circleCenter.x,
            y = (outerRadius - style.fiveStepLineLength.toPx() * 1.5f) * sin(secondAngleInRad) + circleCenter.y,
        )


        //HOUR
        drawLine(
            color = style.hourHandColor,
            strokeWidth = 14f,
            cap = StrokeCap.Round,
            start = hourLineStart,
            end = circleCenter,
        )

        //MINUTE
        drawLine(
            color = style.minuteHandColor,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = minuteLineStart,
            end = circleCenter,
        )
        //SECOND
        drawLine(
            color = style.secondHandColor,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = secondLineStart,
            end = circleCenter,
        )
    }
}

@Preview(showBackground = true)
@Composable()
private fun PreviewClock() {

    TestComposeTheme {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
        ) {
            Clock(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .align(Alignment.Center),
                hour = 3f,
                minute = 20f,
                second = 0f,
            )
        }
    }
}