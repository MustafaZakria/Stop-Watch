package com.zek.stopwatch.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zek.stopwatch.presentation.HomeScreen
import com.zek.stopwatch.presentation.stop_watch.StopWatchScreen
import com.zek.stopwatch.presentation.stopwatch_records.RecordsScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainScreens.HomeScreen.route,
        modifier = modifier
    ) {
        composable(
            route = MainScreens.HomeScreen.route
        ) {
            HomeScreen(navController)
        }
    }
}