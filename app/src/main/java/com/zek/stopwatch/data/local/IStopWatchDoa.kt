package com.zek.stopwatch.data.local

import androidx.room.Insert
import androidx.room.Query
import com.zek.stopwatch.data.entities.StopWatchRecord
import kotlinx.coroutines.flow.Flow

interface IStopWatchDoa {
    fun getStopWatchRecords(): Flow<List<StopWatchRecord>>

    suspend fun addStopWatchRecord(stopWatchRecord: StopWatchRecord)

    suspend fun deleteRecordById(id: Int)

    suspend fun getRecordById(id: Int): StopWatchRecord
}