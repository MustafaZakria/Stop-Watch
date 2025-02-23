package com.zek.stopwatch.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.zek.stopwatch.data.entities.StopWatchRecord
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.flow.first


@RunWith(AndroidJUnit4::class)
@SmallTest
class StopWatchDoaTest {

    private lateinit var dataBase: StopWatchDataBase

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            StopWatchDataBase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = dataBase.close()

    @Test
    fun insertRecord_GetById() = runTest {
        val record = StopWatchRecord(totalTime = 2000, lapTimes = listOf(2, 3))
        dataBase.getStopWatchDoa().addStopWatchRecord(record)

        val result = dataBase.getStopWatchDoa().getRecordById(1)

        assertThat(result, notNullValue())
        assertThat(record.totalTime, `is`(result.totalTime))
        assertThat(record.lapTimes, `is`(result.lapTimes))
    }

    @Test
    fun deleteRecord_getById_returnNull() = runTest {
        val record = StopWatchRecord(totalTime = 2000, lapTimes = listOf(2, 3))
        dataBase.getStopWatchDoa().addStopWatchRecord(record)

        dataBase.getStopWatchDoa().deleteRecordById(1)

        val result = dataBase.getStopWatchDoa().getRecordById(1)

        assertThat(result, nullValue())
    }

    @Test
    fun testGetStopWatchRecords() = runTest {
        // Insert test data
        val record1 = StopWatchRecord(id = 1, totalTime = 1000, lapTimes = listOf(2, 3))
        val record2 = StopWatchRecord(id = 2, totalTime = 2000, lapTimes = listOf(2, 3))
        dataBase.getStopWatchDoa().addStopWatchRecord(record1)
        dataBase.getStopWatchDoa().addStopWatchRecord(record2)

        // Observe the Flow
        val records = dataBase.getStopWatchDoa().getStopWatchRecords().first()

        // Verify the results
        assertThat(records.size, `is`(2))
        assertThat(record1, `is`(records[0]))
        assertThat(record2, `is`(records[1]))
    }
}