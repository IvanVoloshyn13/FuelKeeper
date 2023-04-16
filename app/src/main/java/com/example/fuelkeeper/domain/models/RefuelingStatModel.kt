package com.example.fuelkeeper.domain.models


data class RefuelingStatModel(
    val refuelDate: String,
    val currentMileage: Int,
    val refuelPayment: Double,
    val lastRefuelDistance: Int,
    val fuelAverage: Double,
    val notes: String? = null,
    val fillUp: Boolean
)
