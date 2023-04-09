package com.example.fuelkeeper.domain.usecase.HomeFrag

import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import javax.inject.Inject

class GetFuelAmountLogUseCase @Inject constructor(val refuelRepository: RefuelRepository) {

   fun getFuelAmountLog(): Double {
        var sum = 0.0
        val refuelLogList = refuelRepository.allRefuelList
        for (element in refuelLogList) {
            sum += element.fuelAmount
        }
        return sum
    }
}