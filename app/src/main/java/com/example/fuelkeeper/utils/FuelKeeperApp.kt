package com.example.fuelkeeper.utils

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.fuelkeeper.data.repository.SettingsRepositoryImpl.Companion.IS_NIGHT_MODE_KEY
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Singleton

@HiltAndroidApp
@Singleton
class FuelKeeperApp : Application() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        var isNightMode = sharedPreferences.getInt(IS_NIGHT_MODE_KEY, 0)
        when (isNightMode) {
            MODE_NIGHT_YES -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            MODE_NIGHT_NO -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            MODE_NIGHT_FOLLOW_SYSTEM -> AppCompatDelegate.setDefaultNightMode(
                MODE_NIGHT_FOLLOW_SYSTEM
            )
        }

    }


}
