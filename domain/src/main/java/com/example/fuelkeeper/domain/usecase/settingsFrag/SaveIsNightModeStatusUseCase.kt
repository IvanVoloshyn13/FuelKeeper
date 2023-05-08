package com.example.fuelkeeper.domain.usecase.settingsFrag

import com.example.fuelkeeper.domain.repositoryInterface.SettingsRepository

class SaveIsNightModeStatusUseCase(private val settingsRepository: SettingsRepository) {

    suspend fun saveIsNightMode(isNightMode: Int) =
        settingsRepository.saveIsNightMode(isNightMode = isNightMode)
}