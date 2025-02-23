package com.zek.stopwatch.presentation.stop_watch.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zek.stopwatch.presentation.ui.theme.StopWatchTheme

@Composable
fun LapTimeItem(
    modifier: Modifier = Modifier,
    index: Int,
    time: String
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Transparent
        ),
        shape = RectangleShape
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Lap $index",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                Text(
                    text = time,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
            HorizontalDivider(
                color = Color(0x72484848),
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun LapTimeItemPreview(modifier: Modifier = Modifier) {
    StopWatchTheme {
        LapTimeItem(
            index = 1,
            time = "10:10.10"
        )
    }
}