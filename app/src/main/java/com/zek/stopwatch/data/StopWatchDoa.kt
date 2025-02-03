package com.zek.stopwatch.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zek.stopwatch.data.entities.StopWatchRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface StopWatchDoa {

    @Query("SELECT * FROM StopWatchRecord")
    suspend fun getStopWatchRecords(): Flow<List<StopWatchRecord>>

    @Insert
    suspend fun addStopWatchRecord(stopWatchRecord: StopWatchRecord)
}