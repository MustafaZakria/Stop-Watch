package com.zek.stopwatch.presentation.stop_watch

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zek.stopwatch.R
import com.zek.stopwatch.presentation.components.CircleButtonBox
import com.zek.stopwatch.presentation.components.LapTimeItem
import com.zek.stopwatch.presentation.ui.theme.StopWatchTheme
import com.zek.stopwatch.services.StopWatchService
import com.zek.stopwatch.services.StopWatchService.Companion.isTimeActive
import com.zek.stopwatch.services.StopWatchService.Companion.millis
import com.zek.stopwatch.util.Constants.ACTION_LAP
import com.zek.stopwatch.util.Constants.ACTION_RESET
import com.zek.stopwatch.util.Constants.ACTION_START
import com.zek.stopwatch.util.Constants.ACTION_STOP
import com.zek.stopwatch.util.Mapper.toTimeUiFormat

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun StopWatchScreen() {

    val context = LocalContext.current
    val isTimeActive by isTimeActive.collectAsState()
    val millis by millis.collectAsState()
    val lapTimes = StopWatchService.lapTimes.collectAsState()

    val lapTimesUi = rememberSaveable { mutableStateOf(listOf<Long>()) }

    LaunchedEffect(millis, lapTimes) {
        val newList = lapTimes.value.toMutableList()
        if(newList.size > 0) {
            newList[newList.lastIndex] = millis - lapTimes.value.last()
        } else {
            lapTimesUi.value = listOf()
        }
    }

    StopWatchScreenContent(
        isTimeActive = isTimeActive,
        millis = millis,
        lapItems = lapTimesUi.value,
        onResetClick = {
            val action = if (isTimeActive) {
                ACTION_LAP
            } else {
                ACTION_RESET
            }
            Intent(context, StopWatchService::class.java).also {
                it.action = action
                context.startService(it)
            }
        },
        onStartClick = {
            val action = if (isTimeActive) {
                ACTION_STOP
            } else {
                ACTION_START
            }
            Intent(context, StopWatchService::class.java).also {
                it.action = action
                context.startService(it)
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun StopWatchScreenContent(
    isTimeActive: Boolean,
    millis: Long,
    lapItems: List<Long>,
    onStartClick: () -> Unit,
    onResetClick: () -> Unit
) {

    val textButtonOne = if (isTimeActive) "Stop" else "Start"
    val textColorButtonOne = if (isTimeActive) Color(0xFFFF1B1B) else Color(0xFF40ba0f)
    val colorButtonOne = if (isTimeActive) Color(0x57BA0F0F) else Color(0x5740BA0F)

    val textButtonTwo = if (isTimeActive || millis == 0L) "Lap" else "Reset"

    Scaffold(
        modifier = Modifier
            .fillMaxSize()

    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding),
        ) {
            BoxWithConstraints {
                val maxHeight = maxHeight

                LazyColumn {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,

                            ) {
                            //time
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(maxHeight * 0.4F),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = millis.toTimeUiFormat(),
                                    fontSize = 90.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            //row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircleButtonBox(
                                    backgroundColor = if (millis > 0) Color(0x576C6C6C) else Color(
                                        0x74282828
                                    ),
                                    textColor = if (millis > 0) Color.White else Color(0xFF858585),
                                    text = textButtonTwo,
                                    onClick = { onResetClick.invoke() }
                                )
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_save),
                                    contentDescription = "save",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp).clickable{}
                                )
                                CircleButtonBox(
                                    backgroundColor = colorButtonOne,
                                    textColor = textColorButtonOne,
                                    text = textButtonOne,
                                    onClick = { onStartClick.invoke() }
                                )

                            }
                            HorizontalDivider(
                                color = Color(0x72484848),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }

                    itemsIndexed(lapItems.reversed()) { index, lapItem ->

                        LapTimeItem(index = lapItems.size - index, time = lapItem.toTimeUiFormat())

                        HorizontalDivider(
                            color = Color(0x72484848),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

            }
        }
    }
}




@RequiresApi(Build.VERSION_CODES.S)
@Composable
@Preview(showBackground = true)
fun StopWatchScreenPreview() {
    StopWatchTheme {
        StopWatchScreenContent(
            isTimeActive = false,
            millis = 1163362L,
            lapItems = listOf(1163362L, 1163362L, 1163362L),
            onResetClick = {},
            onStartClick = {}
        )
    }
}
