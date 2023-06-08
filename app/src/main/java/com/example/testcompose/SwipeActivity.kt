package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcompose.ui.theme.TestComposeTheme
import kotlin.math.roundToInt

class SwipeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeTheme {
                SwipeScreen()
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeScreen() {
    val parentBoxWidth = 320.dp
    val childBoxSides = 30.dp

    val swipeableState = rememberSwipeableState("C")
    val widthPx = with(LocalDensity.current) {
        (parentBoxWidth - childBoxSides).toPx()
    }

    val anchors = mapOf(
        0f to "L",
        widthPx / 2 to "C",
        widthPx to "R"
    )

    Box {
        Box(
            modifier = Modifier.padding(20.dp).width(parentBoxWidth).height(childBoxSides)
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal
                )
        ) {
            Box(
                Modifier.fillMaxWidth().height(5.dp).background(Color.DarkGray)
                    .align(Alignment.CenterStart)
            )
            Box(
                Modifier.size(10.dp).background(
                    Color.DarkGray,
                    shape = CircleShape
                ).align(Alignment.CenterStart)
            )
            Box(
                Modifier.size(10.dp).background(
                    Color.DarkGray,
                    shape = CircleShape
                ).align(Alignment.Center)
            )
            Box(
                Modifier.size(10.dp).background(
                    Color.DarkGray,
                    shape = CircleShape
                ).align(Alignment.CenterEnd)
            )

            Box(
                Modifier.offset {
                    IntOffset(
                        swipeableState.offset.value.roundToInt(),
                        0
                    )
                }.size(childBoxSides).background(Color.Blue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    swipeableState.currentValue,
                    color = Color.White,
                    fontSize = 22.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeScreenPreview() {
    TestComposeTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            SwipeScreen()
        }
    }
}