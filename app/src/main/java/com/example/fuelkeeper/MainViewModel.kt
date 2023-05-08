package com.example.fuelkeeper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuelkeeper.domain.usecase.settingsFrag.GetIsNightModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getIsNightModeUseCase: GetIsNightModeUseCase
) : ViewModel() {
    init {
        getIsNightMode()
    }

    private val _isNightMode = MutableStateFlow<Int>(1)
    val isNightMode = _isNightMode.asStateFlow()
    fun getIsNightMode() {
        viewModelScope.launch {
            val result =
                withContext(Dispatchers.IO) { async { getIsNightModeUseCase.getIsNightMode() } }
            _isNightMode.emit(result.await())
        }

    }
}
