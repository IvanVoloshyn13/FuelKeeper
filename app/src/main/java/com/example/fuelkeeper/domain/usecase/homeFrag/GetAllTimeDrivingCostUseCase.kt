package com.example.fuelkeeper.domain.usecase.homeFrag

import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.repositoryInterface.HomeRepository
import javax.inject.Inject

class GetAllTimeDrivingCostUseCase @Inject constructor(val homeRepository: HomeRepository) {
    suspend fun getAllTimeDrivingCost(): Resource<Double> {
        return homeRepository.getAllTimeDrivingCost()
    }
}