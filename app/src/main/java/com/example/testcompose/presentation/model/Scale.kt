package com.example.testcompose.presentation.model

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.example.testcompose.screen.ScaleStyle
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Scale(
    modifier: Modifier = Modifier,
    style: ScaleStyle = ScaleStyle(),
    minWeight: Int = 20,
    maxWeight: Int = 250,
    initialWeight: Int = 80,
    onWeightChange: (Int) -> Unit,
) {
    val radius = style.radius
    val scaleWidth = style.scaleWidth
    var center by remember {
        mutableStateOf(Offset.Zero)
    }
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var angle by remember {
        mutableFloatStateOf(0f)
    }

    Canvas(modifier = modifier) {
        center = this.center
        circleCenter = Offset(center.x, scaleWidth.toPx() / 2f + radius.toPx())

        val outerRadius = radius.toPx() + scaleWidth.toPx() / 2f
        val innerRadius = radius.toPx() - scaleWidth.toPx() / 2f

        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                circleCenter.x,
                circleCenter.y,
                radius.toPx(),
                Paint().apply {
                    strokeWidth = scaleWidth.toPx()
                    color = Color.WHITE
                    setStyle(Paint.Style.STROKE)
                    setShadowLayer(
                        60f,
                        0f,
                        0f,
                        Color.argb(50, 0, 0, 0)
                    )
                }
            )
        }

        //Draw lines
        for (i in minWeight..maxWeight) {

            //Math.PI / 180: É aproximadamente 0,0174. Este é o valor de 1 grau em radianos.
            val angleInRad = (i - initialWeight + angle - 90) * (Math.PI / 180f).toFloat()

            val lineType = when {
                i % 10 == 0 -> LineType.TenStep
                i % 5 == 0 -> LineType.FiveStep
                else -> LineType.Normal
            }

            val lineLength = when(lineType){
                LineType.Normal -> style.normalLineLength.toPx()
                LineType.FiveStep -> style.fiveStepLineLength.toPx()
                LineType.TenStep -> style.tenStepLineLength.toPx()
            }

            val lineColor = when(lineType){
                LineType.Normal -> style.normalLineColor
                LineType.FiveStep -> style.fiveStepLineColor
                LineType.TenStep -> style.tenStepLineColor
            }

            val lineStart = Offset(
                x = (outerRadius - lineLength) * cos(angleInRad) + circleCenter.x,
                y = (outerRadius - lineLength) * sin(angleInRad) + circleCenter.y
            )

            val lineEnd = Offset(
                x = outerRadius * cos(angleInRad) + circleCenter.x,
                y = outerRadius * sin(angleInRad) + circleCenter.y
            )

            drawContext.canvas.nativeCanvas.apply {
                if(lineType is LineType.TenStep){
                    val textRadius = outerRadius - lineLength - 5.dp.toPx() - style.textSize.toPx()

                    val x = textRadius * cos(angleInRad) + circleCenter.x
                    val y = textRadius * sin(angleInRad) + circleCenter.y

                    withRotation(
                        degrees = angleInRad * (180 / PI.toFloat()) + 90f,
                        x,
                        y
                    ){
                        drawText(
                            abs(i).toString(),
                            x,
                            y,
                            Paint().apply {
                                textSize = style.textSize.toPx()
                                textAlign = Paint.Align.CENTER
                            }
                        )
                    }
                }
            }

            drawLine(
                color = lineColor,
                start = lineStart,
                end = lineEnd,
                strokeWidth = 1.dp.toPx()
            )

            val middleTop = Offset(
                x = circleCenter.x,
                y = circleCenter.y - innerRadius - style.scaleIndicatorLength.toPx()
            )

            val bottomLeft = Offset(
                x = circleCenter.x - 4f,
                y = circleCenter.y - innerRadius
            )

            val bottomRight = Offset(
                x = circleCenter.x + 4f,
                y = circleCenter.y - innerRadius
            )

            val indicator = Path().apply {
                moveTo(middleTop.x, middleTop.y)
                lineTo(bottomLeft.x, bottomLeft.y)
                lineTo(bottomRight.x, bottomRight.y)
                lineTo(middleTop.x, middleTop.y)
            }

            drawPath(path = indicator, color = style.scaleIndicatorColor)
        }
    }
}

@Preview(showBackground = true)
@Composable()
private fun PreviewScale() {
    TestComposeTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Scale(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
            ) {

            }
        }
    }
}