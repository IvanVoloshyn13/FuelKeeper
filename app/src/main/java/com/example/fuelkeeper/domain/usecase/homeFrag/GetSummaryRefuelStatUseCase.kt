package com.example.fuelkeeper.domain.usecase.homeFrag

import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.SummaryRefuelStatModel
import com.example.fuelkeeper.domain.repositoryInterface.HomeRepository
import javax.inject.Inject

class GetSummaryRefuelStatUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend fun getSummaryRefuelStat(): Resource<SummaryRefuelStatModel> {
        return homeRepository.getSummaryRefuelStatDetail()
    }
}