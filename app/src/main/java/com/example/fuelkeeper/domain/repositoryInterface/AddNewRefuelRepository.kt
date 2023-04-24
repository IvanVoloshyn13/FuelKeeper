package com.example.fuelkeeper.domain.repositoryInterface

import com.example.fuelkeeper.domain.models.RefuelingModel

interface AddNewRefuelRepository {

    suspend fun addNewRefuelLog(
        newRefuel: RefuelingModel
    ): Boolean
    suspend fun getLastRefuelCurrentMileage(): Int
    fun setLocaleDate(): String
}
