package com.example.fuelkeeper.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "refueling_register")
data class RefuelingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val refuelDate: String,
    val currentMileage: Int,
    val fuelAmount: Double,
    val fuelPricePerLiter: Float,
    val notes: String? = null
)
