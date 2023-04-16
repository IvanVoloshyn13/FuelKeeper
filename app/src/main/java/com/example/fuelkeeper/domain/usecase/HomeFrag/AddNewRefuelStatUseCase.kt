package com.example.fuelkeeper.domain.usecase.HomeFrag

import com.example.fuelkeeper.domain.models.LastRefuelDetailsModel
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.repositoryInterface.RefuelStatisticsRepository
import javax.inject.Inject

class AddNewRefuelStatUseCase @Inject constructor(val refuelStatisticsRepository: RefuelStatisticsRepository) {
    suspend fun addNewRefuelStat(newRefuel: RefuelingModel, newRefuelStat: LastRefuelDetailsModel) {
        refuelStatisticsRepository.addNewRefuelStat(newRefuel, newRefuelStat)
    }
}