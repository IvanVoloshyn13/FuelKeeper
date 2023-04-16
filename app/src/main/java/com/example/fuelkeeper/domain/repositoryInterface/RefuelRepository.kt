package com.example.fuelkeeper.domain.repositoryInterface

import com.example.fuelkeeper.domain.models.RefuelingModel

interface RefuelRepository {

    fun setLocaleDate(): String
    suspend fun getAllRefuelLog(): ArrayList<RefuelingModel>
    suspend fun addNewRefuelLog(refuel: RefuelingModel): Boolean
    suspend fun getFuelAmountLog(): List<Double>
    suspend fun getCurrentMileageLog(): List<Int>


}

