package com.zek.stopwatch.repository

import com.zek.stopwatch.data.entities.StopWatchRecord
import kotlinx.coroutines.flow.Flow

interface IMainRepository {
    suspend fun insertRecord(stopWatchRecord: StopWatchRecord)
    fun getRecords(): Flow<List<StopWatchRecord>>

    suspend fun deleteRecordById(id: Int)
}