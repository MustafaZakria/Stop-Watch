package com.zek.stopwatch.services

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.zek.stopwatch.notification.NotificationUtils.provideBaseNotificationBuilder
import com.zek.stopwatch.notification.NotificationUtils.provideNotificationChannel
import com.zek.stopwatch.util.Constants.ACTION_LAP
import com.zek.stopwatch.util.Constants.ACTION_RESET
import com.zek.stopwatch.util.Constants.ACTION_START
import com.zek.stopwatch.util.Constants.ACTION_STOP
import com.zek.stopwatch.util.Constants.NOTIFICATION_ID
import com.zek.stopwatch.util.Mapper.toTimeUiFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch

class StopWatchService : LifecycleService() {

    private var isFirstTime = true

    private var coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private lateinit var notificationManager: NotificationManager

    private lateinit var baseNotification: NotificationCompat.Builder

    companion object {
        val isTimeActive = MutableStateFlow(false)
        val millis = MutableStateFlow(0L)
        val lapTimes = MutableStateFlow(mutableListOf<Long>())
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate() {
        super.onCreate()

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        baseNotification = provideBaseNotificationBuilder(this)

        notificationManager.createNotificationChannel(
            provideNotificationChannel()
        )
        coroutineScope.launch {
            millis
                .filter { it > 0L }
                .sample(200L)
                .map { it.toTimeUiFormat() }
                .collect { value ->
                    val newNotification = baseNotification.setContentText(value)
                    notificationManager.notify(NOTIFICATION_ID, newNotification.build())
                }
        }
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
            baseNotification.build()
        )
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