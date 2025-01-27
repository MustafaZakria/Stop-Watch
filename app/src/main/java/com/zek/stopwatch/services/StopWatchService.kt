package com.zek.stopwatch.services

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.zek.stopwatch.notification.NotificationUtils.provideBaseNotificationBuilder
import com.zek.stopwatch.notification.NotificationUtils.provideNotificationChannel
import com.zek.stopwatch.util.Constants.ACTION_START
import com.zek.stopwatch.util.Constants.ACTION_STOP
import com.zek.stopwatch.util.Constants.NOTIFICATION_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StopWatchService: LifecycleService() {

    private var isFirstTime = true

    private var coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private lateinit var notificationManager: NotificationManager

    private lateinit var baseNotification: NotificationCompat.Builder

    companion object {
        val isTimeActive = MutableStateFlow(false)
        val millis = MutableStateFlow(0L)
    }

    override fun onCreate() {
        super.onCreate()

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        baseNotification = provideBaseNotificationBuilder(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                provideNotificationChannel()
            )
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTION_START -> {
                if (isFirstTime) {
                    //start
                    startForegroundService()
                    isFirstTime = false
                }
                else {
                    //resume
                    startTimer()
                }
            }
            ACTION_STOP -> {
                isTimeActive.value = false
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForegroundService() {

        startTimer()

        startForeground(
            NOTIFICATION_ID,
            baseNotification.build()
        )
    }

    private var totalTimeInMillis = 0L
    private var lapTime = 0L

    private fun startTimer() {
        isTimeActive.value = true
        val startTime = System.currentTimeMillis()
        coroutineScope.launch {
            while(isTimeActive.value) {
                lapTime = System.currentTimeMillis() - startTime
                millis.value = lapTime + totalTimeInMillis
                delay(10L)
            }
            totalTimeInMillis += lapTime
        }
    }
}