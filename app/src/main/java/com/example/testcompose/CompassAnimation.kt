package com.example.testcompose

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcompose.ui.theme.TestComposeTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CompassAnimation(
    color: Color = Color.Black,
    bearing: Int = 360
) {
    val canvasSize = 300.dp


    // Sample data
    // current angle 340 -> new angle 10 -> diff -330 -> +30
    // current angle 20 -> new angle 350 -> diff 330 -> -30
    // current angle 60 -> new angle 270 -> diff 210 -> -150
    // current angle 260 -> new angle 10 -> diff -250 -> +110

    val degrees = -(bearing - 270)

    val storedRotation = remember { mutableStateOf(bearing) }

    LaunchedEffect(degrees){
        snapshotFlow { degrees  }
            .collectLatest { newRotation ->
                val diff = newRotation - storedRotation.value
                val shortestDiff = when{
                    diff > 180 -> diff - 360
                    diff < -180 -> diff + 360
                    else -> diff
                }
                storedRotation.value = storedRotation.value + shortestDiff
            }
    }

    //negative value to rotate in opsite direction
    // degrees - 270 to put the compass needle on top position
    val angle by animateFloatAsState(
        targetValue = storedRotation.value.toFloat(),
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        )
    )
    Log.d("DEGREES", "${storedRotation.value} degrees")


    val startAngle = angle


    Box(modifier = Modifier
        .size(canvasSize)
        .drawBehind {
            val componentSize = size / 1.25f
            val componentSize2 = componentSize / 1.2f

            compassBorder(
                componentSize = componentSize,
                color = color
            )

            compassNeedle(
                componentSize = componentSize2, startAngle = startAngle,
                color = color
            )

        }
    )

   /* Box(modifier = Modifier
        .size(canvasSize)
        .clip(CircleShape)
        .background(Color.Yellow)
        .drawBehind {
            val componentSize = size / 1.25f
            val componentSize2 = componentSize / 1.2f

            compassBorder(
                componentSize = componentSize,
                color = color
            )

            compassNeedle(
                componentSize = componentSize2, startAngle = startAngle,
                color = color
            )

        }
        .rotate(degrees = degrees),
        contentAlignment = Alignment.Center
    ){
        Text(text = "-->", fontSize = 30.sp, color= color)
    }*/

}

fun DrawScope.compassNeedle(
    componentSize: Size,
    startAngle: Float,
    color: Color
){
    drawArc(
        size = componentSize,
        color = color,
        startAngle = startAngle,
        sweepAngle = 1f, //abertura do angulo
        useCenter = false,
        style = Stroke(
            width = 47f,
            cap = StrokeCap.Round
        ),

        topLeft = Offset(
            //size é o canvasSize
            //centralizando
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

fun DrawScope.compassBorder(
    componentSize: Size,
    color: Color
){
    drawArc(
        size = componentSize,
        color = color,
        startAngle = 0f,
        sweepAngle = 360f, //abertura do angulo
        useCenter = false,
        style = Stroke(
            width = 30f,
            cap = StrokeCap.Round
        ),

        topLeft = Offset(
            //size é o canvasSize
            //centralizando
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

@Composable
@Preview(showBackground = true)
fun CompassAnimationPreview() {
    TestComposeTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {

            CompassAnimation(bearing = 90)
        }
    }
}
