package com.example.fuelkeeper.domain.repositoryInterface

import com.example.fuelkeeper.domain.models.RefuelingModel

interface AddNewRefuelRepository {


    suspend fun getAllRefuelLog(): ArrayList<RefuelingModel>
    suspend fun addNewRefuelLog(
        newRefuel: RefuelingModel,
        lastRefuelMileage: Int
    ): Boolean
    suspend fun getLastRefuelCurrentMileage(): Int
    fun setLocaleDate(): String
}
