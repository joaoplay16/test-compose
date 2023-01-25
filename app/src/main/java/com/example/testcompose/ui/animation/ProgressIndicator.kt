package com.example.testcompose.ui.animation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.testcompose.ui.theme.TestComposeTheme

@Composable
fun ProgressIndicator(
    canvasSize: DpSize = DpSize(width = 300.dp, height = 50.dp),
    indicatorValue: Int = 0,
    maxIndicatorValue: Int = 100,
    backgroundIndicatorColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
    backgroundIndicatorWidth: Float = 50f,
    foregroundIndicatorColor: Color = MaterialTheme.colors.primary,
    foregroundIndicatorStrokeWidth: Float = 50f,
) {

    var allowedIndicatorValue by remember {
        mutableStateOf(maxIndicatorValue)
    }
    allowedIndicatorValue = if (indicatorValue <= maxIndicatorValue){
        indicatorValue
    }else{
        maxIndicatorValue
    }

    var animatedIndicatorValue by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = allowedIndicatorValue ){
        animatedIndicatorValue = allowedIndicatorValue.toFloat()
    }

    val percentage = (animatedIndicatorValue / maxIndicatorValue) * 100

    val animatedProgressPercentage by animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(1000)
    )


    Column(modifier = Modifier
        .size(canvasSize)
        .drawBehind {
            foregroundLine(
                percentageProgress = animatedProgressPercentage,
                componentSize = size,
                indicatorColor = foregroundIndicatorColor,
                indicatorStrokeWidth = foregroundIndicatorStrokeWidth
            )

            backgroundLine(
                componentSize = size,
                indicatorColor = backgroundIndicatorColor.copy(0.1f),
                indicatorStrokeWidth = backgroundIndicatorWidth
            )
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {

    }
}

fun DrawScope.foregroundLine(
    percentageProgress: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float
){
    val endPoint = (percentageProgress / 100) * (componentSize.width )

    drawLine(
        color = indicatorColor,
        start = Offset(x = 0f, y = componentSize.height / 2),
        end =  Offset(x = endPoint, y = componentSize.height / 2),
        strokeWidth =indicatorStrokeWidth ,
        cap = StrokeCap.Round
    )
}

fun DrawScope.backgroundLine(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float
){
    drawLine(
        color = indicatorColor,
        start = Offset(x = 0f, y = componentSize.height / 2),
        end =  Offset(x = componentSize.width , y = componentSize.height / 2),
        strokeWidth =indicatorStrokeWidth ,
        cap = StrokeCap.Round
    )
}



@Composable
@Preview(showBackground = true)
fun ProgressIndicatorPreview() {
    TestComposeTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var value by remember { mutableStateOf(0)}

            ProgressIndicator(
                indicatorValue = value
            )

            Slider(
                value = value.toFloat(),
                onValueChange = { value = it.toInt()},
                steps = 10,
                valueRange = 0f..100f
            )
        }
    }
}
