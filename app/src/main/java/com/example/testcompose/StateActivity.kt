package com.example.testcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
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

                            LaunchedEffect(key1 = Unit, block = {
                                viewModel.isSubmitted.collect{
                                    Toast.makeText(
                                        this@StateActivity,
                                        "email: ${viewModel.state.email.text} - " +
                                        "password: ${viewModel.state.password.text}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

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
