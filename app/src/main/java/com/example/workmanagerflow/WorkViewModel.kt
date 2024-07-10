package com.example.workmanagerflow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkViewModel(private val workExecutor: WorkExecutor) : ViewModel() {

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WorkApp)
                val marsPhotosRepository = application.container.workExecutor
                WorkViewModel(workExecutor = marsPhotosRepository)
            }
        }
    }

    private val _uiState = MutableStateFlow("")
    val uiState = _uiState.asStateFlow()

    init {
        val userId = "userId"

        workExecutor.startWork(userId)

        viewModelScope.launch {
            workExecutor.monitorWork(userId).collect { result ->

                Log.d("WorkViewModel", "monitor Collect = $result")

                _uiState.update {
                    result::class.simpleName.orEmpty()
                }
            }
        }
    }

}