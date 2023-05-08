package com.example.fuelkeeper.domain.repositoryInterface

interface SettingsRepository {

    suspend fun saveIsNightMode(isNightMode: Int)

    suspend fun getIsNightMode(): Int
}