package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.testcompose.ui.theme.TestComposeTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class AdMobActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column (
                        modifier = Modifier.fillMaxSize().scrollable(
                            rememberScrollState(), orientation = Orientation.Vertical)
                    ){
                        Text(text = "BANNER")
                        AdvertView(adSize = AdSize.BANNER)
                        Text(text = "FULL_BANNER")
                        AdvertView(adSize = AdSize.FULL_BANNER)
                        Text(text = "MEDIUM_RECTANGLE")
                        AdvertView(adSize = AdSize.MEDIUM_RECTANGLE)
                        Text(text = "LARGE_BANNER")
                        AdvertView(adSize = AdSize.LARGE_BANNER)
                        Text(text = "LEADERBOARD")
                        AdvertView(adSize = AdSize.LEADERBOARD)
                        Text(text = "FLUID")
                        AdvertView(adSize = AdSize.FLUID)
                        Text(text = "SMART_BANNER")
                        AdvertView(adSize = AdSize.SMART_BANNER)
                        Text(text = "WIDE_SKYSCRAPER")
                        AdvertView(adSize = AdSize.WIDE_SKYSCRAPER)
                    }
                }
            }
        }
    }
}

@Composable
fun AdvertView(modifier: Modifier = Modifier, adSize: AdSize) {
    val isInEditMode = LocalInspectionMode.current
    if (isInEditMode) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(horizontal = 2.dp, vertical = 6.dp),
            textAlign = TextAlign.Center,
            color = Color.White,
            text = "Advert Here",
        )
    } else {
        AndroidView(
            modifier = modifier.fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(adSize)
                    adUnitId = "ca-app-pub-3940256099942544/6300978111"
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AdvertPreview() {
    AdvertView(adSize = AdSize.FULL_BANNER)
}