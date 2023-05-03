package com.example.fuelkeeper.domain.repositoryInterface

import com.example.fuelkeeper.domain.models.RefuelingModel

interface EditRepository {
    suspend fun getRefuelById(id: Int): RefuelingModel?
    suspend fun updateRefuel(refuel: RefuelingModel): Boolean
}