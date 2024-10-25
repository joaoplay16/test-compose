package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testcompose.screen.SheetScreen
import com.example.testcompose.screen.TextScreen
import com.example.testcompose.ui.theme.TestComposeTheme
import kotlinx.serialization.Serializable

@Serializable
data object ScreenX

@Serializable
data object ScreenY

class PredictiveBackGesturesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestComposeTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding),
                            startDestination = ScreenX
                        ) {
                            composable<ScreenX> {
                                TextScreen(
                                    onButtonClick = {
                                        navController.navigate(ScreenY)
                                    }
                                )
                            }
                            composable<ScreenY> {
                                SheetScreen()
                            }
                        }
                    }
            }
        }
    }
}