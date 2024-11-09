package com.example.testcompose

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testcompose.ui.theme.TestComposeTheme

class MasterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestComposeTheme(darkTheme = true) {
                var activityList by remember {
                    mutableStateOf(
                        getActivityListFromManifest()
                    )
                }
                Surface(color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        topBar = {
                            var searchText by remember { mutableStateOf("") }

                            LaunchedEffect(key1 = searchText) {
                                if (searchText.isEmpty()) {
                                    activityList = getActivityListFromManifest()
                                } else {
                                    val regex = searchText.toRegex(RegexOption.IGNORE_CASE)

                                    activityList = getActivityListFromManifest().filter {
                                        regex.containsMatchIn(it.name)
                                    }
                                }
                            }

                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp),
                                value = searchText,
                                onValueChange = { text ->
                                    searchText = text
                                }
                            )
                        }
                    ) { padding ->
                        Column(
                            modifier = Modifier
                                .padding(padding)
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            activityList.sortedBy {
                                it.name.substringAfterLast(".")
                            }.map {
                                Button(onClick = {
                                    startActivity(
                                        Intent().apply {
                                            setClassName(
                                                this@MasterActivity,
                                                it.name
                                            )
                                        }
                                    )
                                }) {
                                    Text(it.name.substringAfterLast("."))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getActivityListFromManifest(): List<ActivityInfo> {
        val activityList = mutableListOf<ActivityInfo>()
        try {
            val packageManager: PackageManager = packageManager
            val packageInfo = packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_ACTIVITIES
            )
            val activities = packageInfo.activities
            if (activities != null) {
                for (activity in activities) {
                    activityList.add(activity)
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return activityList
    }
}