package com.example.fuelkeeper.domain.models

data class RefuelingModel(
    val id: Int? = null,
    val refuelDate: String,
    val currentMileage: Int,
    val fuelAmount: Double,
    val fuelPricePerLiter: Double,
    val notes: String? = null,
    val fillUp: Boolean
)