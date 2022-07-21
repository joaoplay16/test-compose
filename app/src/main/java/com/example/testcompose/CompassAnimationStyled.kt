package com.example.testcomposeimport androidx.compose.animation.core.*import androidx.compose.foundation.backgroundimport androidx.compose.foundation.borderimport androidx.compose.foundation.layout.*import androidx.compose.foundation.shape.CircleShapeimport androidx.compose.foundation.shape.RoundedCornerShapeimport androidx.compose.material.MaterialThemeimport androidx.compose.material.Textimport androidx.compose.runtime.*import androidx.compose.ui.Alignmentimport androidx.compose.ui.Modifierimport androidx.compose.ui.draw.drawBehindimport androidx.compose.ui.draw.rotateimport androidx.compose.ui.geometry.Offsetimport androidx.compose.ui.geometry.Sizeimport androidx.compose.ui.graphics.Colorimport androidx.compose.ui.graphics.StrokeCapimport androidx.compose.ui.graphics.drawscope.DrawScopeimport androidx.compose.ui.graphics.drawscope.Strokeimport androidx.compose.ui.platform.testTagimport androidx.compose.ui.semantics.contentDescriptionimport androidx.compose.ui.semantics.semanticsimport androidx.compose.ui.tooling.preview.Previewimport androidx.compose.ui.unit.Dpimport androidx.compose.ui.unit.dpimport androidx.compose.ui.unit.spimport androidx.constraintlayout.compose.ConstraintLayoutimport com.example.testcompose.ui.theme.TestComposeThemeimport kotlinx.coroutines.NonDisposableHandle.parent@Composablefun CompassAnimationStyled(    canvasSize: Dp = 300.dp,    color: Color = Color.Black,    degrees: Int = 360) {    val (lastRotation, setLastRotation) = remember { mutableStateOf(0) } // this keeps last rotation    var newRotation = lastRotation // newRotation will be updated in proper way    // last rotation converted to range [-359; 359]    val modLast = if (lastRotation > 0) lastRotation % 360 else 360 - (-lastRotation % 360)    // if modLast isn't equal rotation retrieved as function argument    // it means that newRotation has to be updated    if (modLast != degrees) {        // distance in degrees between modLast and rotation going backward        val backward = if (degrees > modLast) modLast + 360 - degrees else modLast - degrees        // distance in degrees between modLast and rotation going forward        val forward = if (degrees > modLast) degrees - modLast else 360 - modLast + degrees        // update newRotation so it will change rotation in the shortest way        newRotation = if (backward < forward) {            // backward rotation is shorter            lastRotation - backward        } else {            // forward rotation is shorter (or they are equal)            lastRotation + forward        }        setLastRotation(newRotation)    }    //negative value to rotate in opsite direction    // degrees - 270 to start the compass needle on top position    val rotation = -(newRotation - 270)    val angle by animateFloatAsState(        targetValue = rotation.toFloat(),        animationSpec = tween(            durationMillis = 300,            easing = LinearEasing        )    )    Column(horizontalAlignment = Alignment.CenterHorizontally) {        Text(            text = getDirectionsLabel(degrees),            color = MaterialTheme.colors.onBackground,            fontSize = (canvasSize.value * .1f).toInt().sp        )        Box(modifier = Modifier            .size(canvasSize)            .border(width = 10.dp, shape = CircleShape, color = color),            contentAlignment = Alignment.Center        ){            Text(                text = "${degrees}º",                color = MaterialTheme.colors.onBackground,                fontSize = (canvasSize.value * .2f).toInt().sp            )            ConstraintLayout(                Modifier.fillMaxSize()                    .rotate(angle)            ) {                val ( west, north, east, south) = createRefs()                Text(                    modifier = Modifier.constrainAs(west){                        top.linkTo(parent.top, 16.dp)                        start.linkTo(parent.start, 16.dp)                        end.linkTo(parent.end, 16.dp)                    },                    text = "W",                    color = MaterialTheme.colors.onBackground,                )                Text(                    modifier = Modifier.constrainAs(east){                        start.linkTo(parent.start, 16.dp)                        end.linkTo(parent.end, 16.dp)                        bottom.linkTo(parent.bottom, 16.dp)                    },                    text = "E",                    color = MaterialTheme.colors.onBackground,                )                Text(                    modifier = Modifier.constrainAs(south){                        top.linkTo(parent.top, 16.dp)                        start.linkTo(parent.start, 16.dp)                        bottom.linkTo(parent.bottom, 16.dp)                    }.rotate(90f),                    text = "S",                    color = MaterialTheme.colors.onBackground,                )                Text(                    modifier = Modifier.constrainAs(north){                        top.linkTo(parent.top, 16.dp)                        end.linkTo(parent.end, 16.dp)                        bottom.linkTo(parent.bottom, 16.dp)                    }.rotate(90f),                    text = "N",                    color = Color.Red,                )            }        }    }}@Composable@Preview(showBackground = true)fun CompassAnimationStyledPreview() {    TestComposeTheme {        Column(            Modifier.fillMaxSize(),            horizontalAlignment = Alignment.CenterHorizontally,            verticalArrangement = Arrangement.Center        ) {            CompassAnimationStyled(degrees = 90)        }    }}