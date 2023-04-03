package com.example.fuelkeeper.domain.repositoryInterface

import androidx.lifecycle.LiveData
import com.example.fuelkeeper.domain.models.RefuelingEntity

interface RefuelRepository {
    suspend fun getAllRefuelLog(): LiveData<List<RefuelingEntity>>
    suspend fun addNewRefuelLog(refuel: RefuelingEntity):Boolean
}