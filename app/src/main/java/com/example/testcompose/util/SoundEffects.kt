package com.example.testcompose.util

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun playSound(context: Context, resId: Int){
    CoroutineScope(Dispatchers.IO).launch {
        val mp = MediaPlayer()

        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mp.setDataSource(context.resources.openRawResourceFd(resId))
            }else{
                val uri: Uri = Uri.parse(
                    "android.resource://${context.packageName}/$resId"
                )
                mp.setDataSource(context, uri)
            }
            mp.prepare()
            mp.start()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}