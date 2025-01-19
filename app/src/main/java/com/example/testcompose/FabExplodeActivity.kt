@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testcompose.screen.FabExplodeScreen
import com.example.testcompose.ui.screens.drawer.MainScreen
import com.example.testcompose.ui.theme.TestComposeTheme
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

@Serializable
data object AddItemRoute

const val FAB_EXPLODE_BOUNDS_KEY = "FAB_EXPLODE_BOUNDS_KEY"

class FabExplodeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestComposeTheme {
                val navController = rememberNavController()
                val fabColor = Color.Green
                SharedTransitionLayout {
                    NavHost(
                        navController = navController,
                        startDestination = MainRoute,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        composable<MainRoute> {
                            FabExplodeScreen(
                                fabColor = fabColor,
                                animatedVisibilityScope = this,
                                onFabClick = {
                                    navController.navigate(AddItemRoute)
                                }
                            )
                        }
                        composable<AddItemRoute> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(fabColor)
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState(
                                            key = FAB_EXPLODE_BOUNDS_KEY
                                        ),
                                        animatedVisibilityScope = this
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Add item")
                            }
                        }
                    }
                }
            }
        }
    }
}