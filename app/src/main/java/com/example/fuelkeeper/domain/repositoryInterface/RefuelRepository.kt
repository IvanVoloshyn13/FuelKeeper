package com.example.fuelkeeper.domain.repositoryInterface

import com.example.fuelkeeper.data.models.RefuelingEntity
import com.example.fuelkeeper.domain.models.RefuelingModel

interface RefuelRepository {

    fun setLocaleDate(): String
    suspend fun getAllRefuelLog(): List<RefuelingEntity>
    suspend fun addNewRefuelLog(refuel: RefuelingModel): Boolean


}

