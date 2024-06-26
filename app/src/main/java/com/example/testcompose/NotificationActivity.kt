package com.example.testcompose

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.testcompose.ui.theme.TestComposeTheme

class NotificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestComposeTheme {
                SimpleNotification()
            }
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "noti"
            val descriptionText = "desc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("0", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    @SuppressLint("MissingPermission")
    @Composable
    fun SimpleNotification(
        modifier: Modifier = Modifier
    ) {
        val builder = NotificationCompat.Builder(this, "0")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Opa eai?")
            .setContentText("Vamo nessa?")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        Button(
            modifier = modifier,
            onClick = {
                with(NotificationManagerCompat.from(this)){
                    notify(1, builder.build())
                }
            }
        ) {
            Text(text = "Mostrar")
        }
    }



    @Preview
    @Composable()
    fun PreviewSimpleNotification() {
        TestComposeTheme {
            Scaffold() { contentPadding ->
                SimpleNotification(Modifier.padding(contentPadding))
            }
        }
    }
}


