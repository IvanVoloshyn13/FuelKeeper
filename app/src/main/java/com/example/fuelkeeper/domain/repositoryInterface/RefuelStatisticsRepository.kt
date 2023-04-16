package com.example.fuelkeeper.domain.repositoryInterface

import com.example.fuelkeeper.domain.models.LastRefuelDetailsModel
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.models.RefuelingStatModel

interface RefuelStatisticsRepository {
    suspend fun addNewRefuelStat(
        newRefuel: RefuelingModel,
        newRefuelStat: LastRefuelDetailsModel
    ): Boolean

    suspend fun getAllRefuelStat(): ArrayList<RefuelingStatModel>
}