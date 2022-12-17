package com.example.testcompose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcompose.ui.theme.TestComposeTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomTimePicker(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.size(width = 350.dp, height = 200.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val hourListState = rememberLazyListState()
        val hourFlingBehavior = rememberSnapFlingBehavior(hourListState)
        val minuteListState = rememberLazyListState()
        val minuteFlingBehavior = rememberSnapFlingBehavior(minuteListState)
        val secondListState = rememberLazyListState()
        val secondFlingBehavior = rememberSnapFlingBehavior(secondListState)

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            state = hourListState,
            flingBehavior = hourFlingBehavior
        ) {
           items(24) { hour ->
               Box(
                   modifier = Modifier
                       .height(68.dp)
                       .width(70.dp)
                       .padding(8.dp)
                       .background(Color.Gray),
                   contentAlignment = Alignment.Center
               ) {
                   Text(hour.toString(), fontSize = 32.sp)
               }
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            state = minuteListState,
            flingBehavior = minuteFlingBehavior
        ) {
            items(60) { minute ->
                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .width(70.dp)
                        .padding(8.dp)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(minute.toString(), fontSize = 32.sp)
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            state = secondListState,
            flingBehavior = secondFlingBehavior
        ) {
            items(60) { second ->
                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .width(70.dp)
                        .padding(8.dp)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(second.toString(), fontSize = 32.sp)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCustomTimePicker(){
    TestComposeTheme {
            CustomTimePicker()
    }
}
