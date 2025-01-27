package com.zek.stopwatch.presentation

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zek.stopwatch.presentation.ui.theme.StopWatchTheme
import com.zek.stopwatch.services.StopWatchService
import com.zek.stopwatch.services.StopWatchService.Companion.isTimeActive
import com.zek.stopwatch.services.StopWatchService.Companion.millis
import com.zek.stopwatch.util.Constants.ACTION_START
import com.zek.stopwatch.util.Constants.ACTION_STOP
import com.zek.stopwatch.util.Mapper.toTimeUiFormat

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StopWatchScreen() {

    val context = LocalContext.current
    val isTimeActive by isTimeActive.collectAsState()
    val millis by millis.collectAsState()

    StopWatchScreenContent(
        isTimeActive = isTimeActive,
        time = millis.toTimeUiFormat(),
        onResetClick = {
            if (isTimeActive) {

            } else {

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

@Composable
fun StopWatchScreenContent(
    isTimeActive: Boolean,
    time: String,
    onStartClick: () -> Unit,
    onResetClick: () -> Unit
) {

    val textButtonOne = if (isTimeActive) "Stop" else "Start"
    val textColorButtonOne = if (isTimeActive) Color(0xFFFF1B1B) else Color(0xFF40ba0f)
    val colorButtonOne = if (isTimeActive) Color(0x57BA0F0F) else Color(0x5740BA0F)

    val textButtonTwo = if (isTimeActive) "Lap" else "Reset"

    Scaffold(
        modifier = Modifier
            .fillMaxSize()

    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .padding(innerPadding),
        ) {
            BoxWithConstraints {
                val maxHeight = maxHeight
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
                            text = time,
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
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircleButtonBox(
                            backgroundColor = Color(0x576C6C6C),
                            textColor = Color.White,
                            text = textButtonTwo,
                            onClick = { onResetClick.invoke() }
                        )

                        CircleButtonBox(
                            backgroundColor = colorButtonOne,
                            textColor = textColorButtonOne,
                            text = textButtonOne,
                            onClick = { onStartClick.invoke() }
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun CircleButtonBox(
    backgroundColor: Color,
    textColor: Color,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(75.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun StopWatchScreenPreview() {
    StopWatchTheme {
        StopWatchScreenContent(
            isTimeActive = false,
            time = 1727572163362L.toTimeUiFormat(),
            onResetClick = {},
            onStartClick = {}
        )
    }
}
