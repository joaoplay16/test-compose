package com.example.testcompose

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcompose.ui.theme.TestComposeTheme

@Composable
fun CompassAnimation(
    canvasSize: Dp = 300.dp,
    color: Color = Color.Black,
    degrees: Int = 360
) {

    val (lastRotation, setLastRotation) = remember { mutableStateOf(0) } // this keeps last rotation
    var newRotation = lastRotation // newRotation will be updated in proper way
    // last rotation converted to range [-359; 359]
    val modLast = if (lastRotation > 0) lastRotation % 360 else 360 - (-lastRotation % 360)

    // if modLast isn't equal rotation retrieved as function argument
    // it means that newRotation has to be updated
    if (modLast != degrees) {
        // distance in degrees between modLast and rotation going backward
        val backward = if (degrees > modLast) modLast + 360 - degrees else modLast - degrees
        // distance in degrees between modLast and rotation going forward
        val forward = if (degrees > modLast) degrees - modLast else 360 - modLast + degrees

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
    // degrees - 270 to start the compass needle on top position
    val rotation = -(newRotation - 270)

    val angle by animateFloatAsState(
        targetValue = rotation.toFloat(),
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        )
    )

    val startAngle = angle

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        val direction =
            if(degrees in 338 .. 360 || degrees in 0 .. 22) "N"
            else if (degrees in 23 .. 67) "NE"
            else if (degrees in 68 .. 112) "L"
            else if (degrees in 113 .. 157) "SE"
            else if (degrees in 158 .. 202) "S"
            else if (degrees in 203 .. 247) "SO"
            else if (degrees in 248 .. 292) "O"
            else if (degrees in 293 .. 337) "NO"
            else ""

        Text(
            text = direction,
            color = MaterialTheme.colors.onBackground,
            fontSize = (canvasSize.value * .1f).toInt().sp
        )

        Box(modifier = Modifier
            .size(canvasSize)
            .drawBehind {
                val componentSize = size / 1.25f
                val componentSize2 = componentSize / 1.230f

                compassBorder(
                    componentSize = componentSize,
                    color = color
                )

                compassNeedle(
                    componentSize = componentSize2, startAngle = startAngle,
                    color = color
                )

            },
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "${degrees}º",
                color = MaterialTheme.colors.onBackground,
                fontSize = (canvasSize.value * .2f).toInt().sp
            )
        }
    }

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
        sweepAngle = size.width * 0.0001f, //abertura do angulo
        useCenter = false,
        style = Stroke(
            width = size.width * 0.06f,
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
            width = size.width * 0.04f,
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

            CompassAnimation(degrees = 90)
        }
    }
}
