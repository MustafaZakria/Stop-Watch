package com.zek.stopwatch.presentation

import androidx.test.filters.MediumTest
import app.cash.turbine.test
import com.zek.stopwatch.MainCoroutineRule
import com.zek.stopwatch.data.entities.StopWatchRecord
import com.zek.stopwatch.repository.FakeMainRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class MainViewModelTest {
    private val records = listOf(
        StopWatchRecord(id = 1, totalTime = 1000, lapTimes = listOf(2, 3)),
        StopWatchRecord(id = 2, totalTime = 1000, lapTimes = listOf(2, 3))
    )

    private lateinit var fakeRepository: FakeMainRepository
    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        fakeRepository = FakeMainRepository(records.toMutableList())
        mainViewModel = MainViewModel(fakeRepository)
    }

    @Test
    fun insertRecord_getUpdatedRecordsFlow() = runTest {

        mainViewModel.records.test {
            assertThat(listOf(), `is`(awaitItem()))

            fakeRepository.insertRecords(records)
            assertThat(records, `is`(awaitItem()))

            val record = StopWatchRecord(id = 3, totalTime = 2000, lapTimes = listOf(2, 3))
            mainViewModel.insertRecord(record)

            assertThat(records + record, `is`(awaitItem()))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun deleteRecordById_getUpdatedRecordsFlow() = runTest {
        mainViewModel.records.test {
            assertThat(listOf(), `is`(awaitItem()))

            mainViewModel.deleteRecordById(1)

            assertThat(1, `is`(awaitItem().size))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun insertRecord_getValueOfSaveLoading() = runTest {
        mainViewModel.isSavingInProgress.test {
            assertEquals(false, (awaitItem()))

            mainViewModel.insertRecord(
                StopWatchRecord(
                    id = 3,
                    totalTime = 2000,
                    lapTimes = listOf(2, 3)
                )
            )
            assertEquals(true, (awaitItem()))

            assertEquals(false, (awaitItem()))
            cancelAndIgnoreRemainingEvents()
        }
    }

}