package com.zek.stopwatch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zek.stopwatch.data.entities.StopWatchRecord
import com.zek.stopwatch.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _records = MutableStateFlow<List<StopWatchRecord>>(listOf())
    val records = _records.asStateFlow()

    private val _isSavingInProgress = MutableStateFlow(false)
    val isSavingInProgress = _isSavingInProgress.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecords().collect {
                _records.value = it
            }
        }
    }

    fun insertRecord(stopWatchRecord: StopWatchRecord) {
        _isSavingInProgress.update { true }
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRecord(stopWatchRecord)
        }
        _isSavingInProgress.update { false }
    }

    fun deleteRecordById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRecordById(id)
        }
    }
}