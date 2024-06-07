package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ContextualFlowRowOverflow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcompose.ui.theme.TestComposeTheme

class ContextualFlowRowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainFlowRow(
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainFlowRow(
    modifier: Modifier = Modifier
) {
    val topics = listOf(
        "Arts & Crafts",
        "Beauty",
        "Books",
        "Business",
        "Comics",
        "Culinary",
        "Design",
        "Fashion",
        "Film",
        "History",
        "Maths",
        "Music",
        "People",
        "Philosophy",
        "Religion",
        "Social sciences",
        "Technology",
        "TV",
        "Writing"
    )

    val DEFAULT_MAX_LINES = 2

    var maxLines by remember { mutableIntStateOf(DEFAULT_MAX_LINES) }

    ContextualFlowRow(
        modifier = modifier.animateContentSize()
            .padding(8.dp),
        itemCount = topics.size,
        maxLines = maxLines,
        overflow = ContextualFlowRowOverflow.expandOrCollapseIndicator(
            expandIndicator = {
                TextButton(
                    onClick = { maxLines += 1 },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.surfaceVariant,
                        containerColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(text = "${this@expandOrCollapseIndicator.totalItemCount - this@expandOrCollapseIndicator.shownItemCount}+ more")
                }
            },
            collapseIndicator = {
                TextButton(
                    onClick = { maxLines = DEFAULT_MAX_LINES },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.errorContainer,
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                    Text(text = "Hide")
                }
            }
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) { index ->
        Button(onClick = { }) {
            Text(text = topics[index])
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainFlowRowPreview() {
    TestComposeTheme {
        MainFlowRow()
    }
}