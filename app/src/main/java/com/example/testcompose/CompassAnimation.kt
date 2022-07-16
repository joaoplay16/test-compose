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

    val (lastRotation, setLastRotation) = remember { mutableStateOf(0) } // this keeps last rotation
    var newRotation = lastRotation // newRotation will be updated in proper way
    // last rotation converted to range [-359; 359]
    val modLast = if (lastRotation > 0) lastRotation % 360 else 360 - (-lastRotation % 360)

    // if modLast isn't equal rotation retrieved as function argument
    // it means that newRotation has to be updated
    if (modLast != bearing) {
        // distance in degrees between modLast and rotation going backward
        val backward = if (bearing > modLast) modLast + 360 - bearing else modLast - bearing
        // distance in degrees between modLast and rotation going forward
        val forward = if (bearing > modLast) bearing - modLast else 360 - modLast + bearing

        // update newRotation so it will change rotation in the shortest way
        newRotation = if (backward < forward) {
            // backward rotation is shorter
            lastRotation - backward
        } else {
            // forward rotation is shorter (or they are equal)
            lastRotation + forward
        }

        setLastRotation(newRotation)
    }

    //negative value to rotate in opsite direction
    // degrees - 270 to put the compass needle on top position
    val degrees = -(newRotation - 270)

    val angle by animateFloatAsState(
        targetValue = degrees.toFloat(),
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearEasing
        )
    )
    Log.d("DEGREES", "${degrees} degrees")

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
