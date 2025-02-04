package com.zek.stopwatch.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TopBarOneButton(
    icon: ImageVector,
    tint: Color,
    arrangement: Arrangement.Horizontal,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 32.dp
        )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = arrangement
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.padding(end = 8.dp)
                .clickable { onClick.invoke() }
                .size(36.dp)
        )
    }
}