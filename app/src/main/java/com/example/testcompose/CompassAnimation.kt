package com.example.testcompose

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcompose.ui.theme.TestComposeTheme

@Composable
fun CompassAnimation(
    color: Color = Color.Black,
    degrees: Float = 360f
) {
    val canvasSize = 300.dp

//    val animatable = remember { Animatable(initialValue = 0f)}
//    LaunchedEffect(key1 = animatable ){
//
//        animatable.animateTo(
//            targetValue = 360f,
//            animationSpec = infiniteRepeatable(
//                animation = keyframes {
//                    durationMillis = 1000
//                    0.0f at 0 with  LinearEasing
//                    360f at durationMillis with LinearEasing
//                },
//                repeatMode = RepeatMode.Restart
//            )
//        )
//    }


    val angle by animateFloatAsState(
        targetValue = degrees,
        animationSpec = tween(durationMillis = 800, easing = LinearEasing)
    )

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
        .rotate(degrees = startAngle),
    ){

    }

/*    Box(modifier = Modifier
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
        .rotate(degrees = startAngle),
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

            CompassAnimation(degrees = 20f)
        }
    }
}
