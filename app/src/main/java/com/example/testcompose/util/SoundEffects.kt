package com.example.testcompose.util

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build

fun playSound(context: Context, resId: Int){
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