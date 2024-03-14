package com.example.testcompose

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testcompose.ui.theme.TestComposeTheme
import kotlinx.coroutines.launch

class SliderActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS

        val images = listOf<Int>(
            R.drawable.forest,
            R.drawable.bee,
            R.drawable.cat,
            R.drawable.combi,
        )

        setContent {
            TestComposeTheme {
                val pagerState = rememberPagerState(pageCount = { images.size })
                val scope = rememberCoroutineScope()
                Box(modifier = Modifier.fillMaxSize()){
                    HorizontalPager(
                        state = pagerState,
                        key = { images[it] },
                        pageSize = PageSize.Fill
                    ) { index ->
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = images[index]),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Box(
                        modifier = Modifier
                            .offset(y = -(16).dp)
                            .fillMaxWidth(0.5f)
                            .clip(RoundedCornerShape(100))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(8.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                         IconButton(
                             modifier = Modifier.align(Alignment.CenterStart),
                             onClick = {
                                 scope.launch {
                                     pagerState.animateScrollToPage(
                                         pagerState.currentPage - 1
                                     )
                                 }
                             }){
                             Icon(
                                 imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null
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
                            }){
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}
