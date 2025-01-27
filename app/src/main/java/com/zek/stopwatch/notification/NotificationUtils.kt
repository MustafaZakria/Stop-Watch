package com.zek.stopwatch.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.zek.stopwatch.util.Constants.NOTIFICATION_CHANNEL_ID
import com.zek.stopwatch.util.Constants.NOTIFICATION_CHANNEL_NAME

object NotificationUtils {


    @RequiresApi(Build.VERSION_CODES.O)
    fun provideNotificationChannel() =
        NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )


    fun provideBaseNotificationBuilder(context: Context) =
        NotificationCompat.Builder(
            context,
            NOTIFICATION_CHANNEL_ID
        ).setAutoCancel(false)
            .setOngoing(true)

}