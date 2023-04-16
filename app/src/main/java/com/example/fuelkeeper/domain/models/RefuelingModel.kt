package com.example.fuelkeeper.domain.models

data class RefuelingModel(
    val refuelDate: String,
    val currentMileage: Int,
    val fuelAmount: Double,
    val fuelPricePerLiter: Double,
    val notes: String? = null,
    val fillUp: Boolean
) : java.io.Serializable