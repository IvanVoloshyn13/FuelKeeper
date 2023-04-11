package com.example.fuelkeeper.domain.models

data class SummaryRefuelLogModel(
    val summaryDistance: Int = 0,
    val summaryPayments: Double = 0.0,
    val summaryFuel: Double = 0.0
)