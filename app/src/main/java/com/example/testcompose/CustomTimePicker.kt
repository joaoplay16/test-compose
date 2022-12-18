package com.example.testcompose

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.testcompose.ui.theme.TestComposeTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomTimePicker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    count: Int,
    size: DpSize = DpSize(128.dp, 128.dp),
    onScrollFinished: (index: Int) -> Int,
    content: @Composable (index: Int) -> Unit
) {
    Row(
        modifier = modifier.size(width = 350.dp, height = 200.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val listState = rememberLazyListState(0)
        val flingBehavior = rememberSnapFlingBehavior(listState)

        val isScrollInProgress = listState.isScrollInProgress

        LaunchedEffect(key1 = startIndex){
            listState.scrollToItem(index = startIndex)
        }

        LaunchedEffect(isScrollInProgress) {
            if(!isScrollInProgress) {
                onScrollFinished(  calculateSnappedItemIndex(listState))
            }
        }

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ){
            Surface(
                modifier = Modifier
                    .size(size.width, size.height / 3),
                shape = RoundedCornerShape(10.dp),
                color = Color.Green.copy(0.3f),
                border = BorderStroke(1.dp, Color.Black)
            ) {}
            LazyColumn(
                modifier = Modifier
                    .height(size.height)
                    .width(size.width),
                state = listState,
                contentPadding = PaddingValues(vertical = size.height / 3),
                flingBehavior = flingBehavior
            ) {
                items(count) { index ->
                    Box(
                        modifier = Modifier
                            .size(size.width, size.height / 3)
                            ,
                        contentAlignment = Alignment.Center
                    ) {

                        content(index)
                    }
                }
            }
        }
    }
}

private fun calculateSnappedItemIndex(lazyListState: LazyListState): Int {

    var currentIndex = lazyListState.firstVisibleItemIndex
    val firstVisibleItemScrollOffset = lazyListState.firstVisibleItemScrollOffset

    if(firstVisibleItemScrollOffset != 0){
        currentIndex ++
    }

    return currentIndex
}

@Composable
@Preview(showBackground = true)
fun PreviewCustomTimePicker(){
    TestComposeTheme {
            CustomTimePicker(
                count = 24,
                startIndex = 10,
                onScrollFinished = {
                    Log.d("FLING", "selected index $it")
                }
            ){ index ->
                Text(text = "$index")
            }
    }
}
