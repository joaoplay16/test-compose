package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeast
import com.example.testcompose.ui.theme.TestComposeTheme

class ConstraintActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Surface(color = MaterialTheme.colorScheme.tertiary) {
                        ConstraintLayoutContent()
                    }
                    Surface(color = MaterialTheme.colorScheme.primary) {
                        LargeConstraintLayout()
                    }
                    Surface(color = MaterialTheme.colorScheme.tertiary) {
                        DecoupledConstraintLayout()
                    }
                }
            }
        }
    }
}

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // Creates references for the three composables
        // in the ConstraintLayout's body
        val (button1, button2, text) = createRefs()


        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(
                    parent.top,
                    margin = 16.dp
                )

            }

        ) {
            Text(text = "Button 1")
        }

        Text(
            text = "Text",
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.constrainAs(text) {
                top.linkTo(
                    button1.bottom,
                    margin = 16.dp
                )

                centerAround(button1.end)
            })

        val barrier = createEndBarrier(
            button1,
            text
        )
        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(
                    parent.top,
                    margin = 16.dp
                )
                start.linkTo(barrier)
            }
        ) {
            Text("Button 2")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ConstraintLayoutContentPreview() {
    TestComposeTheme {
        Surface(color = MaterialTheme.colorScheme.primaryContainer) {
            ConstraintLayoutContent()
        }
    }
}

@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            modifier = Modifier.constrainAs(text) {
                linkTo(
                    start = guideline,
                    end = parent.end
                )
                width = Dimension.preferredWrapContent.atLeast(100.dp)
            },
            text = "This is a very very very very very very very long text",
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LargeConstraintLayoutPreview() {
    TestComposeTheme {
        Surface(color = MaterialTheme.colorScheme.primary) {
            LargeConstraintLayout()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DecoupledConstraintLayout() {
    TestComposeTheme {
        Surface(color = MaterialTheme.colorScheme.tertiary) {
            BoxWithConstraints {
                val constraints = if (maxWidth < maxHeight) {
                    decoupledConstraints(margin = 16.dp) // Portrait constraints
                } else {
                    decoupledConstraints(margin = 32.dp) // Landscape constraints
                }

                ConstraintLayout(constraints) {
                    Button(
                        modifier = Modifier.layoutId("button"),
                        onClick = { /* Do something */ },
                    ) {
                        Text("Button")
                    }

                    Text(
                        modifier = Modifier.layoutId("text"),
                        color = MaterialTheme.colorScheme.onTertiary,
                        text = "Text",
                    )
                }
            }
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(
                parent.top,
                margin = margin
            )
        }
        constrain(text) {
            top.linkTo(
                button.bottom,
                margin
            )
            centerHorizontallyTo(button)
        }
    }
}

