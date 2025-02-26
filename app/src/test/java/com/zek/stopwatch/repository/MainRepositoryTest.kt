package com.zek.stopwatch.repository

import app.cash.turbine.test
import com.zek.stopwatch.MainCoroutineRule
import com.zek.stopwatch.data.local.FakeStopWatchDao
import com.zek.stopwatch.TestDispatcherProvider
import com.zek.stopwatch.data.entities.StopWatchRecord
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainRepositoryTest {

    private val records = listOf(
        StopWatchRecord(id = 1, totalTime = 1000, lapTimes = listOf(2, 3)),
        StopWatchRecord(id = 2, totalTime = 1000, lapTimes = listOf(2, 3))
    )

    private lateinit var fakeStopWatchDao: FakeStopWatchDao
    private lateinit var repository: MainRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        fakeStopWatchDao = FakeStopWatchDao(records.toMutableList())
        repository = MainRepository(fakeStopWatchDao, TestDispatcherProvider(mainCoroutineRule.dispatcher))
    }

    @Test
    fun getRecords_returnsFlowOfRecords() = runTest {
        val result = repository.getRecords().first()

        assertThat(result, `is`(records))
    }

    @Test
    fun deleteRecordById_returnListWithoutDeletedRecord() = runTest {
        repository.deleteRecordById(1)

        val result = repository.getRecords().first()

        assertThat(result.size, `is`(1))
    }

    @Test
    fun addRecord_returnListWithAllRecords() = runTest {
        val record = StopWatchRecord(id = 3, totalTime = 2000, lapTimes = listOf(2, 3))
        repository.insertRecord(record)

        val result = repository.getRecords().first()

        assertThat(result.size, `is`(3))
        assertThat(result, `is`(records + record))
    }

    @Test
    fun addRecord_returnAllRecordsFlow() = runTest {
        val record = StopWatchRecord(id = 3, totalTime = 2000, lapTimes = listOf(2, 3))
        repository.getRecords().test {
            repository.insertRecord(record)

            val result = awaitItem()

            assertThat(result.size, `is`(3))
            assertThat(result, `is`(records + record))
            cancel()
        }
    }

    @Test
    fun addRecord_insertRecordWithExistingId_returnSameListSize() = runTest {
        val record = StopWatchRecord(id = 1, totalTime = 2000, lapTimes = listOf(2, 3))
        repository.insertRecord(record)

        val result = repository.getRecords().first()

        assertThat(result.size, `is`(2))
    }
}