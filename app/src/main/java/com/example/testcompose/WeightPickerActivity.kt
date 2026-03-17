package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testcompose.presentation.model.Scale
import com.example.testcompose.screen.ScaleStyle
import com.example.testcompose.ui.theme.TestComposeTheme

class WeightPickerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestComposeTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    Scale(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        style = ScaleStyle( scaleWidth =  150.dp)
                    ){

                    }
                }
            }
        }
    }
}