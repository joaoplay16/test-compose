package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.testcompose.ui.theme.TestComposeTheme

class NavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DefaultNavHost()
                }
            }
        }
    }
}

@Composable
fun DefaultNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "auth",
    ){
        composable("about"){
            Text(text = "About")
        }
        navigation(
            startDestination = "login",
            route = "auth"
        ){
            composable("home"){
                val viewModel = it.sharedViewModel<SampleViewModel>(navController)

                Column {
                    Text(text = "Home")
                    Text(text = "Logged in? ${viewModel.isLoggedIn}")
                    Button(onClick = {
                        navController.navigate("calendar"){
                            popUpTo("auth"){
                                inclusive = true
                            }
                        }
                    }) {
                        Text(text = "Calendar")
                    }
                }
            }

            composable("login"){
                val viewModel = it.sharedViewModel<SampleViewModel>(navController)

                Column {
                    Button(onClick = {
                        viewModel.setLogged(true)
                        navController.navigate("home"){
                            popUpTo("auth")
                        }
                    }) {
                        Text(text = "Login")
                    }
                    Button(onClick = {
                        navController.navigate("register")
                    }) {
                        Text(text = "Register")
                    }
                    Button(onClick = {
                        viewModel.setLogged(true)
                        navController.navigate("forgot_password")
                    }) {
                        Text(text = "forgot password")
                    }
                }
            }
            composable("register"){
                Column {
                    Text(text = "Register")
                    Button(onClick = {
                        navController.navigate("calendar_entry"){
                            popUpTo("auth"){
                                inclusive = true
                            }
                        }
                    }) {
                        Text(text = "calendar entry")
                    }
                }
            }
            composable("forgot_password"){
                    Text(text = "forgot password")
            }
        }
        navigation(
            startDestination = "calendar_overview",
            route = "calendar"
        ){
            composable("calendar_overview"){
                Column {
                    Text(text = "calendar overview")
                    Button(onClick = {
                        navController.navigate("about"){
                            popUpTo("calendar"){
                                inclusive = true
                            }
                        }
                    }) {
                        Text(text = "About")
                    }
                }
            }
            composable("calendar_entry"){
                Text(text = "calendar entry")
            }
        }
    }
}

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController) : T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}