package com.zek.stopwatch.navigation

import com.zek.stopwatch.util.Constants.HOME_SCREEN
import com.zek.stopwatch.util.Constants.RECORDS_SCREEN
import com.zek.stopwatch.util.Constants.STOP_WATCH_SCREEN

sealed class MainScreens(val route: String) {
    data object HomeScreen : MainScreens(HOME_SCREEN)
    data object StopWatchScreen : MainScreens(STOP_WATCH_SCREEN)
    data object RecordsScreen : MainScreens(RECORDS_SCREEN)
}