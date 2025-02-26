package com.zek.stopwatch.repository

import com.zek.stopwatch.data.entities.StopWatchRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeMainRepository(
    private var records: MutableList<StopWatchRecord>
): IMainRepository  {

    private val _recordsFlow = MutableStateFlow<List<StopWatchRecord>>(records)

    override suspend fun insertRecord(stopWatchRecord: StopWatchRecord) {
        records.removeIf { it.id == stopWatchRecord.id }

        records.add(stopWatchRecord)
        _recordsFlow.update { records.toList() }
    }

    override fun getRecords() = _recordsFlow.asStateFlow()

    override suspend fun deleteRecordById(id: Int) {
        records.removeIf { it.id == id }
        _recordsFlow.update { records.toList() }
    }

    fun insertRecords(records: List<StopWatchRecord>) {
        this.records = records.toMutableList()
        _recordsFlow.update { records.toList() }
    }
}