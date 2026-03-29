package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.example.testcompose.ui.components.ui.theme.TestComposeTheme

class BlendModeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BlendMode(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun BlendMode(modifier: Modifier = Modifier) {
    val imageBitmap = ImageBitmap.imageResource(R.drawable.apotecary)
    val density = LocalDensity.current

    BoxWithConstraints {
        val maxHeightPx = with(density) { maxHeight.toPx() }

        val imageWidthPx = with(density) {
            maxWidth.toPx()
        }

        val aspectRatio = imageBitmap.width.toFloat() / imageBitmap.height.toFloat()

        val imageHeightPx = imageWidthPx / aspectRatio

        val imageTopPx = (maxHeightPx / 2f) - (imageHeightPx / 2f)


        var circleOffset by remember {
            mutableStateOf(
                Offset(
                    x = imageWidthPx / 2, y = (maxHeightPx / 2)
                )
            )
        }

        Canvas(
            modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        circleOffset = Offset(
                            change.position.x, change.position.y
                        )
                    }
                }) {

            val imagePath = Path().apply {
                addRect(
                    Rect(
                        offset = Offset(0f, imageTopPx), size = Size(
                            imageWidthPx, imageHeightPx
                        )
                    )
                )
            }

            clipPath(path = imagePath) {
                drawCircle(
                    color = Color.Red,
                    radius = 240f,
                    blendMode = BlendMode.Xor,
                    center = circleOffset
                )
            }


            drawImage(
                image = imageBitmap,
                dstOffset = IntOffset(
                    0, imageTopPx.toInt()
                ),
                dstSize = IntSize(imageWidthPx.toInt(), imageHeightPx.toInt()),
                blendMode = BlendMode.Luminosity,
            )


        }
    }

}

@Preview(showBackground = true)
@Composable
fun BlendModePreview() {
    TestComposeTheme {
        BlendMode()
    }
}
