package com.example.fuelkeeper.domain.repositoryInterface

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fuelkeeper.domain.models.RefuelingEntity

interface RefuelRepository {

    fun setLocaleDate(): String
    suspend fun getAllRefuelLog()
    suspend fun addNewRefuelLog(refuel: RefuelingEntity): Boolean
    suspend fun getFuelAmountLog(): List<Double>

    val allRefuelList: List<RefuelingEntity>
}

