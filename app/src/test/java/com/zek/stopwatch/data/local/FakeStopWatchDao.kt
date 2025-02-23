package com.zek.stopwatch.data.local

import com.zek.stopwatch.data.entities.StopWatchRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeStopWatchDao(
    private var records: MutableList<StopWatchRecord> = mutableListOf()
) : IStopWatchDoa {

    private val _recordsFlow = MutableStateFlow<List<StopWatchRecord>>(records)

    override fun getStopWatchRecords(): Flow<List<StopWatchRecord>> = _recordsFlow

    override suspend fun addStopWatchRecord(stopWatchRecord: StopWatchRecord) {
        records.removeIf { it.id == stopWatchRecord.id }

        records.add(stopWatchRecord)
        _recordsFlow.update { records.toList() }
    }

    override suspend fun deleteRecordById(id: Int) {
        records.removeIf { it.id == id }
        _recordsFlow.update { records.toList() }
    }

    override suspend fun getRecordById(id: Int): StopWatchRecord {
        return records.firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Record not found")
    }
}