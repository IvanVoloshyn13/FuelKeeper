package com.example.fuelkeeper.domain.usecase.HomeFrag

import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import javax.inject.Inject

class GetFuelPaymentsSumUseCase @Inject constructor(val repository: RefuelRepository) {

    fun getSumOfPayments(): Double {
        var sum = 0.0
        val refuelList = repository.allRefuelList
        if (refuelList.isNotEmpty()) {
            for (elements in refuelList) {
                sum += (elements.fuelPricePerLiter * elements.fuelAmount)
            }
        }
        return sum
    }

}