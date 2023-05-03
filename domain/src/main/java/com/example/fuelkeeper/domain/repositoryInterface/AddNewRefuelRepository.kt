package com.example.fuelkeeper.domain.repositoryInterface

import com.example.fuelkeeper.domain.models.RefuelingModel

interface AddNewRefuelRepository {

    suspend fun addNewRefuelLog(newRefuel: RefuelingModel): Boolean
    fun setLocaleDate(): String
}
