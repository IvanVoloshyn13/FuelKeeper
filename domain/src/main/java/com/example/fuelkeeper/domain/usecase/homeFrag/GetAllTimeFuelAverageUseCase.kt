package com.example.fuelkeeper.domain.usecase.homeFrag
import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.repositoryInterface.HomeRepository
class GetAllTimeFuelAverageUseCase  (private val homeRepository: HomeRepository) {

   suspend fun getAllTimeFuelAverage(): Resource<Double> {
        return homeRepository.getAllTimeFuelAverage()
    }

}