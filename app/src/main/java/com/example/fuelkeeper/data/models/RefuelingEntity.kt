package com.example.fuelkeeper.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refueling_register")
data class RefuelingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val refuelDate: String,
    val currentMileage: Int,
    val fuelAmount: Double,
    val fuelPricePerLiter: Double,
    val notes: String? = null,
    val fillUp: Boolean
)
