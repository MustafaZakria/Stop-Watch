package com.zek.stopwatch.presentation.stopwatch_records.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zek.stopwatch.data.entities.StopWatchRecord
import com.zek.stopwatch.presentation.stop_watch.components.LapTimeItem
import com.zek.stopwatch.presentation.ui.theme.StopWatchTheme
import com.zek.stopwatch.util.Mapper.toTimeUiFormat

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RecordItem(
    record: StopWatchRecord
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color(0xFF101010)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = record.totalTime.toTimeUiFormat(),
                fontSize = 40.sp,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )

            record.lapTimes.reversed().apply {
                for (index in this.indices) {
                    LapTimeItem(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        index = record.lapTimes.size - index,
                        time = this[index].toTimeUiFormat()
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true)
@Composable
fun RecordItemPreview() {
    StopWatchTheme {
        RecordItem(
            StopWatchRecord(0, 1200, listOf(1000L, 1000L, 1000L))
        )
    }
}