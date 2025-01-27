package com.zek.stopwatch.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Mapper {


    @RequiresApi(Build.VERSION_CODES.O)
    fun Long.toTimeUiFormat(): String {
        val timeZone = Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())

        return timeZone.format(DateTimeFormatter.ofPattern("mm:ss.SS"))
    }

}