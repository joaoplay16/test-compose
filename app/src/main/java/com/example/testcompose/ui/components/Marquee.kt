package com.example.testcompose.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.testcompose.ui.theme.TestComposeTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Marquee() {
    Text(
        modifier = Modifier.basicMarquee(
            iterations = 3,
            animationMode = MarqueeAnimationMode.Immediately
        ),
        text = "Are you interested in learning about futures trading" +
                " but don’t know where to start? This comprehensive " +
                "guide is tailored to beginners and will cover everything" +
                " you need to know about futures transactions."
    )
}

@Preview
@Composable
fun PreviewMarquee() {
    TestComposeTheme {
        Surface {
            Marquee()
        }
    }
}