package com.zek.stopwatch.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class StopWatchRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val totalTime: String,
    val lapTimes: String
)
