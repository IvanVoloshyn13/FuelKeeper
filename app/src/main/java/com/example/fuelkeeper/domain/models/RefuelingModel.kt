package com.example.fuelkeeper.domain.models

import java.util.Date

data class RefuelingModel(
    val refuelDate: String,
    val currentMileage: Int,
    val fuelAmount: Double,
    val fuelPricePerLiter: Double,
    val notes: String? = null,
    val fillUp: Boolean
)