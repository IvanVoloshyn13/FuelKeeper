package com.example.fuelkeeper.domain.models

data class LastRefuelDetailsModel(
    val lastRefuelDistance: Int = 0,
    val lastRefuelFuelAverage: Double = 0.0,
    val lastRefuelPayment: Double = 0.0
)