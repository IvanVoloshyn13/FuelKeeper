package com.example.fuelkeeper.domain.models


data class RefuelingStatModel(
    val id: Int? = null,
    val refuelDate: String,
    val currentMileage: String,
    val refuelPayment: String,
    val lastRefuelDistance: Int,
    val fuelAverage: String,
    val notes: String? = null,
    val fillUp: Boolean
)
