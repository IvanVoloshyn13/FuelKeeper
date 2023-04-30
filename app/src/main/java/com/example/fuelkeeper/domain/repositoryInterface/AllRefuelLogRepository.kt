package com.example.fuelkeeper.domain.repositoryInterface

import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.models.RefuelingStatModel

interface AllRefuelLogRepository {

    suspend fun getAllRefuelLog(): Resource<List<RefuelingStatModel>>
    suspend fun deleteRefuelLog(id: Int)
    suspend fun returnDeletedElement(itemId: Int): Resource<RefuelingModel>
    suspend fun insertDeletedElement(refuel: RefuelingModel)

}