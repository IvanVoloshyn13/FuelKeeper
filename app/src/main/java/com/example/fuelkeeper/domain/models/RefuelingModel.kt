package com.example.fuelkeeper.domain.models

import java.util.Date

data class RefuelingModel(
    val refuelDate: Date,
    val currentMileage: Int,
    val fuelAmount: Double,
    val fuelPrice: Float,
    val notes: String
)