package com.zek.stopwatch.repository

import com.zek.stopwatch.data.entities.StopWatchRecord
import com.zek.stopwatch.data.local.IStopWatchDoa
import com.zek.stopwatch.util.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val stopWatchDoa: IStopWatchDoa,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun insertRecord(stopWatchRecord: StopWatchRecord) {
        withContext(dispatcherProvider.io) {
            stopWatchDoa.addStopWatchRecord(stopWatchRecord)
        }
    }

    fun getRecords() = stopWatchDoa
        .getStopWatchRecords()

    suspend fun deleteRecordById(id: Int) {
        withContext(dispatcherProvider.io) {
            stopWatchDoa.deleteRecordById(id)
        }
    }
}