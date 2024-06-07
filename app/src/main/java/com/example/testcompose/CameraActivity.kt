package com.example.testcompose

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.UseCase
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Autorenew
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CameraActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraPreviewScreen()
        }
    }
}

suspend fun ImageCapture.takePicture(executor: Executor): File {
    val photoFile = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            File.createTempFile(
                "image",
                "jpg"
            )
        }.getOrElse { ex ->
            Log.e(
                "TakePicture",
                "Failed to create temporary file",
                ex
            )
            File("/dev/null")
        }
    }

    return suspendCoroutine { continuation ->
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    continuation.resume(photoFile)
                }

                override fun onError(ex: ImageCaptureException) {
                    Log.e(
                        "TakePicture",
                        "Image capture failed",
                        ex
                    )
                    continuation.resumeWithException(ex)
                }
            })
    }
}

@Composable
fun CameraPreviewScreen() {
    val context = LocalContext.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { permissionGranted ->
        hasCameraPermission = permissionGranted
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (hasCameraPermission) {
            val emptyImageUri = Uri.parse("file://dev/null")
            var imageUri by remember { mutableStateOf(emptyImageUri) }
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()
            val lifecycleOwner = LocalLifecycleOwner.current
            var previewUseCase by remember {
                mutableStateOf<UseCase>(
                    androidx.camera.core.Preview.Builder().build()
                )
            }
            var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }

            val imageCaptureUseCase by remember {
                mutableStateOf(
                    ImageCapture.Builder()
                        .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                        .build()
                )
            }

            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                onUseCase = {
                    previewUseCase = it
                }
            )
            LaunchedEffect(
                previewUseCase,
                cameraSelector
            ) {
                val cameraProvider = context.getCameraProvider()
                try {
                    // Must unbind the use-cases before rebinding them.
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        previewUseCase,
                        imageCaptureUseCase
                    )
                } catch (ex: Exception) {
                    Log.e(
                        "CameraCapture",
                        "Failed to bind camera use cases",
                        ex
                    )
                }
            }
            if (imageUri != emptyImageUri) {
                Column(modifier = Modifier) {
                    Image(
                        modifier = Modifier.size(
                            250.dp,
                            250.dp
                        ),
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "Captured image"
                    )
                    Button(
                        onClick = {
                            imageUri = emptyImageUri
                        }
                    ) {
                        Text("Remove image")
                    }
                }
            } else {
                Column {

                    CaptureButton(
                        hasCameraPermission,
                        cameraLauncher,
                        onClick = {
                            coroutineScope.launch {
                                imageCaptureUseCase.takePicture(context.executor).let { file ->
                                    imageUri = file.toUri()
                                }
                            }
                        }
                    )

                    Button(onClick = {
                        cameraSelector =
                            if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                                CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Autorenew,
                            contentDescription = null
                        )
                    }
                }
            }
        } else {
            IconButton(onClick = { cameraLauncher.launch(Manifest.permission.CAMERA) }) {
                Icon(
                    Icons.Outlined.CameraAlt,
                    contentDescription = "Câmera"
                )
            }
        }
    }
}


suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { future ->
        future.addListener(
            {
                continuation.resume(future.get())
            },
            executor
        )
    }
}

val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    onUseCase: (UseCase) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView = PreviewView(context).apply {
                this.scaleType = scaleType
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            onUseCase(
                androidx.camera.core.Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
            )
            previewView
        }
    )
}


@Composable
fun CaptureButton(
    hasCameraPermission: Boolean,
    cameraLauncher: ActivityResultLauncher<String>,
    onClick: () -> Unit
) {
    Button(onClick = {
        if (hasCameraPermission) {
            onClick()
        } else {
            cameraLauncher.launch(Manifest.permission.CAMERA)
        }
    }) {
        Icon(
            Icons.Outlined.CameraAlt,
            contentDescription = "Capturar"
        )
    }
}
