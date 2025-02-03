package com.zek.stopwatch.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class StopWatchRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val totalTime: Long,
    val lapTimes: List<Long>
)
