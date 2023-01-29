package com.example.testcompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class DownloadActivity : ComponentActivity() {

    private var storagePermissionGranted  by mutableStateOf(false)

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            storagePermissionGranted = isGranted
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    DownloadScreen(
                        requestStoragePermission = {
                            requestPermissionLauncher.launch(
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                        },
                        storagePermissionGranted = storagePermissionGranted
                    )
                }
            }
        }
    }
}

@Composable
fun DownloadScreen(
    modifier: Modifier = Modifier,
    storagePermissionGranted: Boolean = false,
    requestStoragePermission: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val coroutineScope = rememberCoroutineScope()

        var downloadPercentage by remember{ mutableStateOf(0) }

        Row {
            var url by remember {
                mutableStateOf("https://www.nhc.noaa.gov/video/otk_0313_hurricanestormsurge_sm.mov")
            }

            TextField(
                modifier = modifier
                    .weight(2f)
                    .clip(RoundedCornerShape(100.dp)),
                value = url,
                onValueChange = {
                    url = it
                },
                maxLines = 1,
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
                    if(storagePermissionGranted){
                        coroutineScope.launch(Dispatchers.IO) {
                            val downloadPath = Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            try {
                                download(
                                    url = url,
                                    saveDir = downloadPath.absolutePath,
                                    percentage = {   percentage ->
                                        downloadPercentage = percentage
                                    }
                                )
                            }catch (e: Exception){
                                e.printStackTrace()
                            }
                        }
                    }else{
                        requestStoragePermission()
                    }
                }

            ) {
                Icon(
                    imageVector = Icons.Filled.Download,
                    contentDescription = "Search icon"
                )
            }
        }

        ProgressIndicator(indicatorValue = downloadPercentage)

        if(!storagePermissionGranted){
            Text(text = "Storage permission needed!")
        }
    }
}

@Throws(IOException::class, MalformedURLException::class)
suspend fun download(url: String, saveDir: String, percentage: (Int) -> Unit) {

    if (!url.startsWith("http") || url.isBlank()) {
        throw MalformedURLException("invalid url")
    }

    val bufferSize = 4096

    withContext(Dispatchers.IO){

        val connection = URL(url).openConnection() as HttpURLConnection

        val inputStream = connection.inputStream

        val responseCode = connection.responseCode

        if (responseCode == HttpURLConnection.HTTP_OK) {
            val disposition = connection.getHeaderField("Content-Disposition")
            val contentType = connection.contentType
            val contentLength = connection.contentLength


            Log.d("DOWNLOAD", "$contentType\n $contentLength\n $disposition")

            val fileName = url.substring(
                url.lastIndexOf("/") + 1,
                url.length
            )

            val saveFilePath = "$saveDir${File.separator}$fileName"

            Log.d("DOWNLOAD", "saveFilePath $saveFilePath")

            val outputStream = FileOutputStream(saveFilePath)

            val buffer = ByteArray(bufferSize)

            var bytesRead: Int

            var downloadPercentage: Int

            while ((inputStream.read(buffer).also { bytesRead = it }) != -1) {
                outputStream.write(buffer, 0, bytesRead)
                downloadPercentage = ((outputStream.channel.size().toDouble() / contentLength.toDouble()) * 100).toInt()
                percentage(downloadPercentage)
            }

            connection.disconnect()
            inputStream.close()
            outputStream.close()
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewDownloadScreen() {
    TestComposeTheme {
        Surface {
            DownloadScreen(
                storagePermissionGranted = true,
                requestStoragePermission = {}
            )
        }
    }
}