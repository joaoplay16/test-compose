package com.example.testcompose

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.testcompose.ui.theme.TestComposeTheme
import com.example.testcompose.ui.screens.drawer.MainScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class CustomDrawerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestComposeTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
                    MainScreen()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMainContent() {
    TestComposeTheme {
        Surface {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                MainScreen()
            }
        }
    }
}