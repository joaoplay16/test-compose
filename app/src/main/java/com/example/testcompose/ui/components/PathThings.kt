package com.example.testcompose.ui.components

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2

@Composable
fun DrawPath(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val path = Path().apply {
            moveTo(1000f, 100f)

            lineTo(100f, 500f)
            lineTo(500f, 500f)
//            quadraticTo(800f, 300f, 500f, 100f)
            cubicTo(800f, 500f, 800f, 100f, 500f, 100f)
//            close()
        }
        drawPath(
            path = path, color = Color.Red, style = Stroke(
                width = 10.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Miter, miter = 10f
            )
        )
    }
}

@Composable
fun DrawPathOperation(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val squareWithoutOp = Path().apply {
            addRect(Rect(Offset(200f, 200f), Size(200f, 200f)))
        }
        val circle = Path().apply {
            addOval(Rect(Offset(200f, 200f), 100f))
        }
        val pathWithOp = Path().apply {
            op(squareWithoutOp, circle, PathOperation.Xor)
        }
        drawPath(
            path = squareWithoutOp, color = Color.Red, style = Stroke(width = 2.dp.toPx())
        )
        drawPath(
            path = circle, color = Color.Blue, style = Stroke(width = 2.dp.toPx())
        )
        drawPath(
            path = pathWithOp,
            color = Color.Green,
        )
    }
}

@Composable
fun DrawPathAnimation(modifier: Modifier = Modifier) {
    val pathPortion = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(key1 = true) {
        pathPortion.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2500
            )
        )
    }
    val path = Path().apply {
        moveTo(100f, 100f)
        quadraticTo(400f, 400f, 100f, 400f)
    }
    val outPath = android.graphics.Path()
    val pos = FloatArray(2)
    val tan = FloatArray(2)
    android.graphics.PathMeasure().apply {
        setPath(path.asAndroidPath(), false)
        getSegment(0f, pathPortion.value * length, outPath, true)
        getPosTan(pathPortion.value * length, pos, tan)
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        drawPath(
            path = outPath.asComposePath(),
            color = Color.Red,
            style = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round)
        )

        val x = pos[0]
        val y = pos[1]
        val degrees = -atan2(tan[0], tan[1]) * (180f / PI.toFloat()) - 180f
        rotate(degrees = degrees, pivot = Offset(x, y)) {
            drawPath(
                path = Path().apply {
                    moveTo(x, y - 30f)
                    lineTo(x - 30f, y + 60f)
                    lineTo(x + 30f, y + 60f)
                    close()
                },
                color = Color.Red
            )
        }
    }
}

@Composable
fun DrawPathClipping(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val circle = Path().apply {
            addOval(Rect(center = Offset(400f, 400f), radius = 300f))
        }
        drawPath(
            path = circle,
            color = Color.Black,
            style = Stroke(width = 5.dp.toPx())
        )
        clipPath(
            path = circle
        ) {
            drawRect(
                color = Color.Red,
                topLeft = Offset(400f, 400f),
                size = Size(400f, 400f)
            )
        }
    }
}

@Composable
fun DrawTextOnPath(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val path = android.graphics.Path().apply {
            moveTo(0f, 800f)
            quadTo(size.width / 2f, 300f, size.width, 800f)
        }
        drawContext.canvas.nativeCanvas.apply {
            drawTextOnPath(
                "Hello World!",
                path,
                0f,
                -60f,
                Paint().apply {
                    color = android.graphics.Color.RED
                    textSize = 130f
                    textAlign = Paint.Align.CENTER
                }
            )
        }
        drawPath(
            path = path.asComposePath(),
            color = Color.Black,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(50f, 30f)
                )
            )
        )
    }
}

@Composable
fun DrawPathEffects(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10000f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing)
        )
    )
    Canvas(modifier = modifier.fillMaxSize()) {
        val oval1 = Path().apply {
            addOval(
                Rect(
                    topLeft = Offset(
                        300f, 300f
                    ), bottomRight = Offset(800f, 500f)
                )
            )
        }
        val path2 = Path().apply {
            moveTo(-10000f, 800f)
            cubicTo(1000f, 800f, 300f, 1200f, size.width + 3000f, 4000f)
        }
        val star = PathParser()
            .parsePathString("M570.5,252.5l93.8,190c1.5,3.1 4.5,5.3 8,5.8l209.7,30.5c8.7,1.3 12.2,11.9 5.9,18.1L736.1,644.8c-2.5,2.4 -3.6,5.9 -3,9.4L768.8,863c1.5,8.7 -7.6,15.2 -15.4,11.2l-187.5,-98.6c-3.1,-1.6 -6.8,-1.6 -9.9,0l-187.5,98.6c-7.8,4.1 -16.9,-2.5 -15.4,-11.2L389,654.1c0.6,-3.4 -0.5,-6.9 -3,-9.4L234.2,496.9c-6.3,-6.1 -2.8,-16.8 5.9,-18.1l209.7,-30.5c3.4,-0.5 6.4,-2.7 8,-5.8l93.8,-190C555.4,244.7 566.6,244.7 570.5,252.5z")
            .toPath()
        drawPath(
            path = oval1,
            color = Color.Red,
            style = Stroke(
                width = 5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(50f, 30f),
                    phase = phase
                )
            )
        )
        scale(scale = 0.2f) {
            drawPath(
                path = path2,
                color = Color.Red,
                style = Stroke(
                    width = 5.dp.toPx(),
                    pathEffect = PathEffect.stampedPathEffect(
                        shape = star,
                        advance = 1000f,
                        phase = -phase * 10f,
                        style = StampedPathEffectStyle.Rotate
                    )
                )
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDrawPathEffects() {
    DrawPathEffects()
}

@Preview(showBackground = true)
@Composable
private fun PreviewDrawTextOnPath() {
    DrawTextOnPath()
}

@Preview(showBackground = true)
@Composable
private fun PreviewDrawPathClipping() {
    DrawPathClipping()
}

@Preview(showBackground = true)
@Composable
private fun PreviewDrawPathAnimation() {
    DrawPathAnimation()
}


@Preview(showBackground = true)
@Composable
private fun PreviewDrawPathOperation() {
    DrawPathOperation()
}

@Preview(showBackground = true)
@Composable
private fun DrawPathPreview() {
    DrawPath()
}