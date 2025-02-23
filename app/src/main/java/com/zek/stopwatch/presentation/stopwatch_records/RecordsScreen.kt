package com.zek.stopwatch.presentation.stopwatch_records

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zek.stopwatch.R
import com.zek.stopwatch.data.entities.StopWatchRecord
import com.zek.stopwatch.presentation.MainViewModel
import com.zek.stopwatch.presentation.components.TopBarOneButton
import com.zek.stopwatch.presentation.stopwatch_records.components.RecordItem

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RecordsScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val records by viewModel.records.collectAsState()


    RecordsScreenContent(
        records = records,
        onNavigateBack = {onNavigateBack.invoke()}
    )
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RecordsScreenContent(
    records: List<StopWatchRecord>,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopBarOneButton(
                icon = ImageVector.vectorResource(R.drawable.ic_arrow_left),
                tint = Color(0xFFFF9800),
                arrangement = Arrangement.Start,
                onClick = {
                    onNavigateBack.invoke()
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(innerPadding)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                    items(records) { record ->

                        RecordItem(record)

                    }
            }
        }
    }
}