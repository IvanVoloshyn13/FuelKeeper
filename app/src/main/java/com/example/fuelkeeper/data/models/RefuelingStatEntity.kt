package com.example.fuelkeeper.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refueling_stat")
data class RefuelingStatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val refuelDate: String,
    val currentMileage: Int,
    val refuelPayment: Double,
    val lastRefuelDistance: Int,
    val fuelAverage: Double,
    val notes: String? = null,
    val fillUp: Boolean
)