package com.example.fuelkeeper.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuelkeeper.domain.usecase.settingsFrag.GetIsNightModeUseCase
import com.example.fuelkeeper.domain.usecase.settingsFrag.SaveIsNightModeStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val saveIsNightModeStatusUseCase: SaveIsNightModeStatusUseCase,
) :
    ViewModel() {

    fun saveIsNightModeStatus(isNightMode: Int) {
        viewModelScope.launch {
            saveIsNightModeStatusUseCase.saveIsNightMode(isNightMode = isNightMode)
        }
    }

}