package com.example.fuelkeeper.domain.usecase.HomeFrag

import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.repositoryInterface.HomeRepository
import javax.inject.Inject

class GetAllTimeFuelAverageUseCase @Inject constructor(private val homeRepository: HomeRepository) {

   suspend fun getAllTimeFuelAverage(): Resource<Double> {
        return homeRepository.getAllTimeFuelAverage()
    }
}