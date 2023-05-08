package com.example.fuelkeeper.domain.usecase.settingsFrag

import com.example.fuelkeeper.domain.repositoryInterface.SettingsRepository

class GetIsNightModeUseCase(private val settingsRepository: SettingsRepository) {
    suspend fun getIsNightMode() = settingsRepository.getIsNightMode()
}