package com.example.testcompose.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.center
import com.example.testcompose.R
import kotlin.math.roundToInt

@Composable
fun BlendModeScreen(modifier: Modifier = Modifier) {


    val imageBmp = ImageBitmap.imageResource(id = R.drawable.apotecary)

    var circlePos by remember {
        mutableStateOf(Offset.Zero)
    }
    val radius = 200f
    Canvas(modifier = modifier
        .pointerInput(true) {
            detectDragGestures { change, _ ->
                circlePos = change.position
            }
        }
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    circlePos = it
                }
            )
        }
        .onSizeChanged { size ->
            circlePos = Offset(x = size.center.x.toFloat(), y = size.center.y.toFloat())
        }) {

        val bmpHeight =
            ((imageBmp.height.toFloat() / imageBmp.width.toFloat()) * size.width).roundToInt()

        val circlePath = Path().apply {
            addArc(
                oval = Rect(circlePos, radius),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 360f
            )
        }
        drawImage(
            image = imageBmp,
            dstSize = IntSize(
                size.width.roundToInt(), bmpHeight
            ),
            dstOffset = IntOffset(0, center.y.roundToInt() - bmpHeight / 2),
            colorFilter = ColorFilter.tint(Color.Black, BlendMode.Color)
        )
        clipPath(circlePath, clipOp = ClipOp.Intersect) {
            drawImage(
                image = imageBmp,
                dstSize = IntSize(
                    size.width.roundToInt(), bmpHeight
                ),
                dstOffset = IntOffset(0, center.y.roundToInt() - bmpHeight / 2),
            )
        }

        drawCircle(
            color = Color.White,
            radius = radius,
            center = circlePos,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BlendModeScreenPreview() {
    BlendModeScreen(Modifier.fillMaxSize())
}