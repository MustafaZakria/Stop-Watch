package com.zek.stopwatch.navigation

import com.zek.stopwatch.util.Constants.STOP_WATCH_SCREEN

sealed class MainScreen(val route: String) {
    data object StopWatchScreen : MainScreen(STOP_WATCH_SCREEN)
}