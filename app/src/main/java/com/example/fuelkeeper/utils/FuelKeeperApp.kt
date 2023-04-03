package com.example.fuelkeeper.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Singleton

@HiltAndroidApp
@Singleton
class FuelKeeperApp : Application() {
}