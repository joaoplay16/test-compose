package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcompose.ui.theme.TestComposeTheme

class LayoutsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeTheme {
                PhotographerCard()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LayoutsCodeLab() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Layouts CodeLab")
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }            )
        },
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                    label = { Text("OI") },
                    selected = false,
                    onClick = {  }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                    label = { Text("OI") },
                    selected = false,
                    onClick = {  }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                    label = { Text("OI") },
                    selected = false,
                    onClick = {  }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                    label = { Text("OI") },
                    selected = false,
                    onClick = {  }
                )

            }
        }
    ) { innerPadding ->
        PhotographerCard(Modifier.padding(innerPadding))
    }
}

@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row (
        modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = {}) // A ordem importa!
    ){
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        ) {
            // Image goes here
        }
        Column (
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
                ){
            Text("Alfred Sisley", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme
                .onSurface.copy(alpha = 0.7f)) {
                Text("3 minutes ago", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PhotographerCardPreview() {
    TestComposeTheme {
        PhotographerCard()
    }
}
