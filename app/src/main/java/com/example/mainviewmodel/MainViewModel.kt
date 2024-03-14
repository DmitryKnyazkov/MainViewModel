package com.example.mainviewmodel

import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    val _stateFlow = MutableStateFlow(100)
    val stateFlow = _stateFlow.asStateFlow()

    val stateFlow2 = MutableStateFlow(1)

    fun countingDown() {
        stateFlow2.value = 2

        _stateFlow.value = 100
        val scope = CoroutineScope(Job())

        scope.launch {
            while (_stateFlow.value != 0) {
                _stateFlow.value--
                delay(100)
            }
            stateFlow2.value = 3

        }
    }
}