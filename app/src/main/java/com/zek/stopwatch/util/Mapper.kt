package com.zek.stopwatch.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration

object Mapper {


    @RequiresApi(Build.VERSION_CODES.S)
    fun Long.toTimeUiFormat(): String {
        val duration = Duration.ofMillis(this)
        val minutes = duration.toMinutes()
        val seconds = duration.seconds % 60
        val millis = duration.toMillisPart() / 10 // Get 2 digits for milliseconds
        return "%02d:%02d.%02d".format(minutes, seconds, millis)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun Long.toLapTimeUiNotification(): String {
        val duration = Duration.ofMillis(this)
        val minutes = duration.toMinutes()
        val seconds = duration.seconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }
}