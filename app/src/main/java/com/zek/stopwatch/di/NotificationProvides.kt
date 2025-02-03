package com.zek.stopwatch.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DecoratedCustomViewStyle
import com.zek.stopwatch.R
import com.zek.stopwatch.presentation.MainActivity
import com.zek.stopwatch.util.Constants.NOTIFICATION_CHANNEL_ID
import com.zek.stopwatch.util.Constants.NOTIFICATION_CHANNEL_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object NotificationProvides {

    @Provides
    @ServiceScoped
    @RequiresApi(Build.VERSION_CODES.O)
    fun provideNotificationChannel() =
        NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )

    @Provides
    @ServiceScoped
    fun provideBaseNotificationBuilder(@ApplicationContext context: Context) =
        NotificationCompat.Builder(
            context,
            NOTIFICATION_CHANNEL_ID
        )
            .setStyle(DecoratedCustomViewStyle())
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Stop Watch")
            .setOnlyAlertOnce(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )

    @Provides
    @ServiceScoped
    fun provideNotificationManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}