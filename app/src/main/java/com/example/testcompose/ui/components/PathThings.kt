package com.example.testcompose.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
            targetValue = 1f, animationSpec = tween(
                durationMillis = 2500
            )
        )
    }
    val path = Path().apply {
        moveTo(100f, 100f)
        quadraticTo(400f, 400f, 100f, 400f)
    }
    val outPath = Path()
    PathMeasure().apply {
        setPath(path, false)
        getSegment(
            0f,
            pathPortion.value * length,
            outPath,
            true
        )
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        drawPath(
            path = outPath,
            color = Color.Red,
            style = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round)
        )
    }
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