package com.example.testcompose

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcompose.ui.theme.TestComposeTheme
import com.example.testcompose.util.CustomGestureListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

class SwipeActivity : ComponentActivity() {
    private lateinit var gestureDetector: GestureDetector
    private var screenWithInPixels: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenWithInPixels = resources.displayMetrics.widthPixels

        gestureDetector = GestureDetector(
            this,
            CustomGestureListener(screenWithInPixels)
        )

        setContent {
            TestComposeTheme {
                HalfSwipeScreen()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        screenWithInPixels = resources.displayMetrics.widthPixels

        event?.let {
            gestureDetector.onTouchEvent(event)
        }
        return super.onTouchEvent(event)
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

@Composable
fun HalfSwipeScreen() {
    val screenWidthInPixels = LocalDensity.current.run {
        LocalConfiguration.current.screenWidthDp.dp.toPx().toInt()
    }
    val screenHeightInPixels = LocalDensity.current.run {
        LocalConfiguration.current.screenHeightDp.dp.toPx().toInt()
    }

    // The half of the screen
    val startOfSwipeAreaX = (screenWidthInPixels * 0.5).toInt()

    // Range of the swipeable area
    val swipeAreaX = (startOfSwipeAreaX..screenWidthInPixels)

    // Acceptable distance for a horizontal swipe - half of the swipeable area
    val swipeThresholdX = startOfSwipeAreaX * 0.5
    // Acceptable distance for a vertical swipe - half of the swipeable area
    val swipeThresholdY = screenHeightInPixels * 0.5

    var initialMotionEvent by remember { mutableStateOf(Offset.Zero) }
    var finalMotionEvent by remember { mutableStateOf(Offset.Zero) }
    val travelDistanceX by remember { derivedStateOf { finalMotionEvent.x - initialMotionEvent.x } }
    val travelDistanceY by remember { derivedStateOf { finalMotionEvent.y - initialMotionEvent.y } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Green.copy(0.2f)
            )
            .pointerInput(Unit) {
                CoroutineScope(Dispatchers.Main).launch {
                    detectDragGestures(
                        onDragStart = { offset ->
                            initialMotionEvent = offset
                        },
                        onDrag = { change, _ ->
                            change.consume()

                            finalMotionEvent = change.position
                        },
                        onDragEnd = {
                            // Check whether touch event is in the swipe area
                            if (initialMotionEvent.x.toInt() in swipeAreaX) {
                                // Check whether the swipe distance has reached the acceptable distance for a swipe
                                // Check whether the swipe is horizontal
                                if (abs(travelDistanceX) > abs(travelDistanceY)) {
                                    if (abs(travelDistanceX).toInt() >= swipeThresholdX) {
                                        if (travelDistanceX > 0) {
                                            // Swipe left to right
                                            Log.d(
                                                "GESTURE",
                                                "HalfSwipeScreen: left to right"
                                            )
                                        } else {
                                            // Swipe right to left
                                            Log.d(
                                                "GESTURE",
                                                "HalfSwipeScreen: right to left"
                                            )
                                        }
                                    }
                                } else {
                                    Log.d(
                                        "GESTURE",
                                        "HalfSwipeScreen: travelDistanceX ${
                                            abs(travelDistanceY)
                                                .toInt()
                                        } > $swipeThresholdY"
                                    )
                                    if (abs(travelDistanceY).toInt() >= swipeThresholdY) {
                                        if (travelDistanceY > 0) {
                                            // Swipe upward to downward
                                            Log.d(
                                                "GESTURE",
                                                "HalfSwipeScreen: upward to downward"
                                            )
                                        } else {
                                            // Swipe downward to upward
                                            Log.d(
                                                "GESTURE",
                                                "HalfSwipeScreen: downward to upward"
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    )
                }
            }
    )
}

@Preview(showBackground = true)
@Composable
fun HalfSwipeScreenPreview() {
    TestComposeTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            HalfSwipeScreen()
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