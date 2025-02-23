package com.zek.stopwatch.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.zek.stopwatch.navigation.MainScreens
import com.zek.stopwatch.presentation.stop_watch.StopWatchScreen
import com.zek.stopwatch.presentation.stopwatch_records.RecordsScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = {2})
    val scope = rememberCoroutineScope()


    HorizontalPager(
        state = pagerState,
    ) { pageIndex ->
        when(pageIndex) {
            0 -> StopWatchScreen(
                navigateToRecordsScreen = {
                    scope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }
            )
            1 -> RecordsScreen(
                onNavigateBack = {
                    scope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }
            )
        }
    }

}