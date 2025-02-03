package com.zek.stopwatch.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.zek.stopwatch.R
import com.zek.stopwatch.notification.NotificationUtils.provideBaseNotificationBuilder
import com.zek.stopwatch.notification.NotificationUtils.provideNotificationChannel
import com.zek.stopwatch.util.Constants.ACTION_LAP
import com.zek.stopwatch.util.Constants.ACTION_RESET
import com.zek.stopwatch.util.Constants.ACTION_START
import com.zek.stopwatch.util.Constants.ACTION_STOP
import com.zek.stopwatch.util.Constants.NOTIFICATION_ID
import com.zek.stopwatch.util.Mapper.ToLapTimeUiNotification
import com.zek.stopwatch.util.Mapper.toTimeUiFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StopWatchService : LifecycleService() {

    private var isFirstTime = true

    private lateinit var coroutineScope: CoroutineScope

    @Inject
    private lateinit var notificationManager: NotificationManager

    @Inject
    private lateinit var baseNotificationBuilder: NotificationCompat.Builder

    @Inject
    private lateinit var notificationChannel: NotificationChannel

    private lateinit var customView: RemoteViews

    companion object {
        val isTimeActive = MutableStateFlow(false)
        val millis = MutableStateFlow(0L)
        val lapTimes = MutableStateFlow(mutableListOf<Long>())
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate() {
        super.onCreate()
        coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        customView = RemoteViews(packageName, R.layout.notification_stopwatch)

        baseNotificationBuilder.setCustomContentView(customView)

        notificationManager.createNotificationChannel(
            notificationChannel
        )

        coroutineScope.launch {
            isTimeActive.collect { isActive ->
                updateNotificationActions(isActive)
            }
        }
    }

    private fun updateNotificationActions(active: Boolean) {
        Log.d("ACTIVE**", active.toString())
        customView.setImageViewResource(
            R.id.iv_stop,
            if (active) R.drawable.ic_stop else R.drawable.ic_start
        ) // Show pause icon
        customView.setImageViewResource(
            R.id.iv_reset,
            if (active) R.drawable.ic_lap else R.drawable.ic_reset
        ) // Show pause icon

        val pendingIntentStart = PendingIntent.getService(
            this,
            1,
            Intent(this, StopWatchService::class.java).apply {
                action = if (active) ACTION_STOP else ACTION_START
            },
            PendingIntent.FLAG_MUTABLE
        )
        val pendingIntentReset = PendingIntent.getService(
            this,
            2,
            Intent(this, StopWatchService::class.java).apply {
                action = if (active) ACTION_LAP else ACTION_RESET
            },
            PendingIntent.FLAG_MUTABLE
        )

        customView.setOnClickPendingIntent(R.id.iv_stop, pendingIntentStart)
        customView.setOnClickPendingIntent(R.id.iv_reset, pendingIntentReset)

        notificationManager.notify(NOTIFICATION_ID, baseNotificationBuilder.build())
    }


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                if (isFirstTime) {
                    //start
                    startForegroundService()
                    isFirstTime = false
                } else {
                    //resume
                    startTimer()
                }
            }

            ACTION_STOP -> {
                isTimeActive.value = false
            }

            ACTION_LAP -> {
                lapTimes.value = lapTimes.value.apply {
                    set(lastIndex, millis.value - last())
                    add(millis.value)
                }
            }

            ACTION_RESET -> {
                stopForegroundService()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }


    @RequiresApi(Build.VERSION_CODES.S)
    private fun startForegroundService() {
        startTimer()

        lapTimes.value = mutableListOf(0)

        startForeground(
            NOTIFICATION_ID,
            baseNotificationBuilder.build()
        )

        coroutineScope.launch {
            millis
                .filter { it > 0L }
                .sample(200L)
                .collect { value ->
                    customView.setTextViewText(R.id.tv_timer, value.toTimeUiFormat())

                    if (lapTimes.value.size > 1) {
                        val lapValue = (value - lapTimes.value.last()).ToLapTimeUiNotification()
                        customView.setTextViewText(
                            R.id.tv_lap,
                            "Lap ${lapTimes.value.size}  $lapValue "
                        )
                        customView.setViewVisibility(R.id.tv_lap, View.VISIBLE)
                    }

                    notificationManager.notify(NOTIFICATION_ID, baseNotificationBuilder.build())
                }
        }
    }

    private fun stopForegroundService() {
        lapTimes.value = mutableListOf()
        totalTimeInMillis = 0L
        millis.value = 0L
        isFirstTime = true
        coroutineScope.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private var totalTimeInMillis = 0L
    private var lapTime = 0L

    private fun startTimer() {
        isTimeActive.value = true
        val startTime = System.currentTimeMillis()
        coroutineScope.launch {
            while (isTimeActive.value) {
                lapTime = System.currentTimeMillis() - startTime
                millis.value = lapTime + totalTimeInMillis
                delay(100L)
            }
            totalTimeInMillis += lapTime
        }
    }

}