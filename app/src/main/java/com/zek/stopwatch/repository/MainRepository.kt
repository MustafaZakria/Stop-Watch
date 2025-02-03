package com.zek.stopwatch.repository

import com.zek.stopwatch.data.StopWatchDoa
import com.zek.stopwatch.data.entities.StopWatchRecord
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val stopWatchDoa: StopWatchDoa
) {
    fun insertRecord(stopWatchRecord: StopWatchRecord) {
        stopWatchDoa.addStopWatchRecord(stopWatchRecord)
    }

    fun getRecords() = stopWatchDoa
        .getStopWatchRecords()

}