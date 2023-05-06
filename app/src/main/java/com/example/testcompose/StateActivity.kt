package com.example.testcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testcompose.ui.screens.formscreen.FormScreen
import com.example.testcompose.ui.screens.formscreen.FormViewModel
import com.example.testcompose.ui.theme.TestComposeTheme

class StateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val viewModel by viewModels<FormViewModel>()
                    NavHost(
                        navController = navController,
                        startDestination = "HOME"){
                        composable("HOME"){
                            val isSubmitted by viewModel.isSubmitted.collectAsState()

                            LaunchedEffect(key1 = isSubmitted, block = {
                                if(isSubmitted == true)
                                Toast.makeText(
                                    this@StateActivity,
                                    "Submitted? = $isSubmitted - email: ${viewModel.state.email} - " +
                                            "password: ${viewModel.state.password}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            })


                            FormScreen(
                                state = viewModel.state,
                                onEvent = viewModel::onEvent)
                        }
                    }
                }
            }
        }
    }
}
