package com.example.fuelkeeper.domain.usecase.homeFrag

import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.LastRefuelDetailsModel
import com.example.fuelkeeper.domain.repositoryInterface.HomeRepository
import javax.inject.Inject

class GetLastRefuelDetailUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend fun getLastRefuelDetail(): Resource<LastRefuelDetailsModel> {
        return homeRepository.getLastRefuelDetail()
    }
}