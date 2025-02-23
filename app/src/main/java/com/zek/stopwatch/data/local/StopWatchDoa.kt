package com.zek.stopwatch.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zek.stopwatch.data.entities.StopWatchRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface StopWatchDoa : IStopWatchDoa {
    @Query("SELECT * FROM StopWatchRecord")
    override fun getStopWatchRecords(): Flow<List<StopWatchRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun addStopWatchRecord(stopWatchRecord: StopWatchRecord)

    @Query("DELETE FROM StopWatchRecord WHERE id = :id")
    override suspend fun deleteRecordById(id: Int)

    @Query("SELECT * FROM StopWatchRecord WHERE id = :id")
    override suspend fun getRecordById(id: Int): StopWatchRecord
}