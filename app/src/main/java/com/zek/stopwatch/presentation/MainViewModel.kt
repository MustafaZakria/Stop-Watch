package com.zek.stopwatch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zek.stopwatch.data.entities.StopWatchRecord
import com.zek.stopwatch.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: MainRepository
): ViewModel()
{
    private val _records = MutableStateFlow<List<StopWatchRecord>?>(null)
    val records = _records.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecords().collect {
                _records.value = it
            }
        }
    }

    fun insertRecord(stopWatchRecord: StopWatchRecord) {
        repository.insertRecord(stopWatchRecord)
    }
}