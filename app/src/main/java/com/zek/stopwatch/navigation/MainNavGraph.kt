package com.zek.stopwatch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zek.stopwatch.presentation.stop_watch.StopWatchScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier
) {

    NavHost(
        navController = navController,
        startDestination = MainScreen.StopWatchScreen.route,
        modifier = modifier
    ) {
        composable(
            route = MainScreen.StopWatchScreen.route
        ) {
            StopWatchScreen()
        }

    }
}