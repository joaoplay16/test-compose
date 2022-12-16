package com.example.testcompose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcompose.ui.theme.TestComposeTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SnapFlingBehavior(
    modifier: Modifier = Modifier
) {
    val state = rememberLazyListState()

    LazyRow(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        state = state,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
    ) {
        items(200) {
            Box(
                modifier = Modifier
                    .height(400.dp)
                    .width(200.dp)
                    .padding(8.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(it.toString(), fontSize = 32.sp)
            }
        }
    }
}

@Preview
@Composable
fun PreviewSnapFlingBehavior() {
    TestComposeTheme {
        SnapFlingBehavior()
    }
}