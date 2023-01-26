package com.example.testcompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Link
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcompose.ui.animation.ProgressIndicator
import com.example.testcompose.ui.theme.TestComposeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DownloadScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DownloadScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val storagePermissionState = rememberPermissionState(
            permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val coroutineScope = rememberCoroutineScope()
        Row {
            var url by remember { mutableStateOf("") }

            TextField(
                modifier = modifier
                    .weight(2f)
                    .clip(RoundedCornerShape(100.dp)),
                value = url,
                onValueChange = {
                    url = it
                },
                placeholder = { Text(text = "Url") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Link,
                        contentDescription = "Search icon"
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.onSecondary.copy(0.3f),
                    focusedIndicatorColor = Color.Unspecified,
                    unfocusedIndicatorColor = Color.Unspecified,
                    disabledIndicatorColor = Color.Unspecified
                )
            )

            Button(
                modifier = Modifier
                    .weight(0.4f)
                    .height(IntrinsicSize.Max)
                    .clip(RoundedCornerShape(100.dp)),
                onClick = {
                    storagePermissionState.launchPermissionRequest()

                    coroutineScope.launch(Dispatchers.IO) {
                        download(
                            "http://jdbc.postgresql.org/download/postgresql-9.2-1002.jdbc4.jar",
                            "/emulated/0"
                        )
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Download,
                    contentDescription = "Search icon"
                )
            }
        }

        ProgressIndicator()

        if(!storagePermissionState.status.isGranted){
            Text(text = "Storage permission needed!")
        }
    }
}

fun download(url: String, saveDir: String) {
    val BUFFER_SIZE = 4096

    val connection = URL(url).openConnection() as HttpURLConnection

    val inputStream = connection.inputStream

    val responseCode = connection.responseCode;

    if (responseCode == HttpURLConnection.HTTP_OK) {
        var fileName = "javafile.jar"
//        val disposition = connection.getHeaderField("Content-Disposition")
        val contentType = connection.contentType
        val contentLength = connection.contentLength

        Log.d("DOWNLOAD", "$contentType\n $contentLength")

//        if (disposition != null) {
//            val index = disposition.indexOf("filename=")
//            if (index > 0) {
//                fileName = disposition.substring(
//                    index + 10,
//                    disposition.length - 1
//                )
//            }
//        } else {
//            fileName = url.substring(
//                url.lastIndexOf("/") + 1,
//                url.length
//            )
//        }

        val saveFilePath = "$saveDir${File.separator}$fileName"

        val baos = FileOutputStream(saveFilePath)

        val buffer = ByteArray(BUFFER_SIZE)

        var bytesRead = -1

        while ((inputStream.read(buffer).also { bytesRead = it }) != -1) {
            baos.write(buffer, 0, bytesRead)
        }

        connection.disconnect()
        inputStream.close()
        baos.close()
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewDownloadScreen() {
    TestComposeTheme {
        Surface() {
            DownloadScreen()
        }
    }
}