package com.example.caravan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    init {
        viewModelScope.launch {
            delay(1000L)
            _spalsh.value = false
        }
    }

    private val _spalsh = MutableStateFlow(true)
    val spalsh = _spalsh.asStateFlow()

    var firstScreen = ""


}

