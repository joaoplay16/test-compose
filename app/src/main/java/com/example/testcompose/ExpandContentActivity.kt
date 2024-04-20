package com.example.testcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.testcompose.ui.theme.TestComposeTheme
import kotlinx.coroutines.launch

class ExpandContentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {

                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainExpandableContent(modifier: Modifier = Modifier) {
    val images = listOf(
        R.drawable.forest,
        R.drawable.bee,
        R.drawable.cat,
        R.drawable.combi,
    )

    val pagerState = rememberPagerState(pageCount = { images.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
        val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
        val contentPadding = screenWidthDp / 4

        HorizontalPager(
            modifier = Modifier.height((screenHeightDp.value * 0.8).dp),
            state = pagerState,
            key = { images[it] },
            pageSpacing = (screenWidthDp.value * 0.07).dp,
            snapPosition = SnapPosition.Start,
            contentPadding = PaddingValues(horizontal = contentPadding)
        ) { index ->
            Image(
                modifier = Modifier.width((screenWidthDp.value * .9).dp),
                painter = painterResource(id = images[index]),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }
        Box(
            modifier = Modifier
                .offset(y = -(16).dp)
                .fillMaxWidth(0.5f)
                .clip(RoundedCornerShape(100))
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp)
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage - 1
                        )
                    }
                }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null
                )
            }
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage + 1
                        )
                    }
                }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }

    }
}


@PreviewScreenSizes
@PreviewLightDark
@Composable
fun ExpandContentPreview() {
    TestComposeTheme {
        Surface {
            MainExpandableContent(Modifier.fillMaxSize())
        }
    }
}