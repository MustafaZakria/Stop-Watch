package com.zek.stopwatch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zek.stopwatch.data.entities.StopWatchRecord
import com.zek.stopwatch.repository.IMainRepository
import com.zek.stopwatch.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: IMainRepository
) : ViewModel() {
    private val _records = MutableStateFlow<List<StopWatchRecord>>(listOf())
    val records = _records.onStart {
        loadRecords()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    private val _isSavingInProgress = MutableStateFlow(false)
    val isSavingInProgress = _isSavingInProgress.asStateFlow()

    private fun loadRecords() {
        viewModelScope.launch {
            repository.getRecords().collect {
                _records.value = it
            }
        }
    }

    fun insertRecord(stopWatchRecord: StopWatchRecord) {
        _isSavingInProgress.update { true }
        viewModelScope.launch {
            try {
                repository.insertRecord(stopWatchRecord)
            } finally {
                _isSavingInProgress.update { false }
            }
        }
    }

    fun deleteRecordById(id: Int) {
        viewModelScope.launch {
            repository.deleteRecordById(id)
        }
    }
}