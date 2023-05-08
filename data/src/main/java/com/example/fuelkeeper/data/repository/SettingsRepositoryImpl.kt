package com.example.fuelkeeper.data.repository

import android.content.SharedPreferences
import com.example.fuelkeeper.domain.repositoryInterface.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    SettingsRepository {


    override suspend fun saveIsNightMode(isNightMode: Int): Unit = withContext(Dispatchers.IO) {
        val sharedEditor = sharedPreferences.edit()
        sharedEditor.putInt(IS_NIGHT_MODE_KEY, isNightMode)
        sharedEditor.commit()
    }

    override suspend fun getIsNightMode(): Int = withContext(Dispatchers.IO) {
        return@withContext sharedPreferences.getInt(IS_NIGHT_MODE_KEY, 0)
    }

    companion object {

        const val IS_NIGHT_MODE_KEY = "NightModeKey"
    }
}