package com.example.testcompose.util

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

class CustomGestureListener(private val screenWithInPixels: Int)
    : GestureDetector.SimpleOnGestureListener() {

    override fun onFling(
        initialMotionEvent: MotionEvent?,
        finalMotionEvent: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (initialMotionEvent == null) return false

        val distanceX = finalMotionEvent.x - initialMotionEvent.x
        val distanceY = finalMotionEvent.y - initialMotionEvent.y

        // The half of the screen
        val startOfSwipeAreaX = (screenWithInPixels * 0.5).toInt()

        // The end of the screen
        val endOfSwipeaAreaX = screenWithInPixels

        // Range of the swipeable area
        val swipeAreaX = (startOfSwipeAreaX .. endOfSwipeaAreaX)

        // Acceptable distance as swipe - half of the swipeable area
        val xSwipeThreshold = startOfSwipeAreaX * 0.5

        // Check whether touch event is in the swipe area
        if (initialMotionEvent.x.toInt() in swipeAreaX ) {
            // Check whether the swipe distance has reached the acceptable distance for a swipe
            if (abs(distanceX).toInt() >= xSwipeThreshold) {
                // Check whether the swipe is horizontal
                if (abs(distanceX) > abs(distanceY)) {
                    if (distanceX > 0) {
                        // Swipe left to right
                        Log.d(
                            "GESTURE",
                            "swipe $distanceX velocityX $velocityX " +
                                    " initialMotionEvent.x ${initialMotionEvent.x} " +
                                    "finalMotionEvent.x ${finalMotionEvent.x} swipeAreaX " +
                                    "$swipeAreaX xThreshold $xSwipeThreshold"
                        )
                    } else {
                        // Swipe right to left
                        Log.d(
                            "GESTURE",
                            "swipe $distanceX velocityX $velocityX " +
                                    " initialMotionEvent.x ${initialMotionEvent.x} " +
                                    "finalMotionEvent.x ${finalMotionEvent.x} swipeAreaX " +
                                    "$swipeAreaX xThreshold $xSwipeThreshold"
                        )

                    }
                } else {
                    if (distanceY > 0) {
                        // Swipe upward to downward
                    } else {
                        // Swipe downward to upward
                    }
                }

            }
        }
            return true
    }
}
