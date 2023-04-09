package com.example.fuelkeeper.domain.usecase.HomeFrag

import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import javax.inject.Inject

class GetSummaryDistanceUseCase @Inject constructor(val repository: RefuelRepository) {

    fun getSummaryDistance(): Int {
        val currentRefuelList = repository.allRefuelList
        val firstRefuelDistance = currentRefuelList.firstOrNull()?.currentMileage
        val lastRefuelDistance = currentRefuelList.lastOrNull()?.currentMileage
        if (lastRefuelDistance != null && firstRefuelDistance != null) {
            return lastRefuelDistance - firstRefuelDistance
        } else return 0
    }
}