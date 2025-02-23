package com.zek.stopwatch.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zek.stopwatch.data.entities.StopWatchRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface StopWatchDoa {

    @Query("SELECT * FROM StopWatchRecord")
    fun getStopWatchRecords(): Flow<List<StopWatchRecord>>

    @Insert
    suspend fun addStopWatchRecord(stopWatchRecord: StopWatchRecord)

    @Query("DELETE FROM StopWatchRecord WHERE id = :id")
    suspend fun deleteRecordById(id: Int)
}