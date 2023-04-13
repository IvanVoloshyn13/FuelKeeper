package com.example.fuelkeeper.domain.usecase.HomeFrag

import com.example.fuelkeeper.domain.models.LastRefuelDetailsModel
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import java.text.DecimalFormat
import javax.inject.Inject

class GetLastRefuelDetailUseCase {

    fun getLastRefuelDetails(refuelList: ArrayList<RefuelingModel>): LastRefuelDetailsModel {
        val df = DecimalFormat("#.##")

        if (refuelList.size > 1) {
            val lastRefuelEntity = refuelList.lastOrNull()
            val secondLastRefuelEntity = refuelList[refuelList.size - 2]
            if (lastRefuelEntity != null && secondLastRefuelEntity != null) {
                val lastRefuelDistance =
                    lastRefuelEntity.currentMileage - secondLastRefuelEntity.currentMileage
                val lastRefuelFuelAverage =
                    df.format(lastRefuelEntity.fuelAmount / lastRefuelDistance * 100).toDouble()
                val lastRefuelPayment =
                    lastRefuelEntity.fuelPricePerLiter * lastRefuelEntity.fuelAmount
                return LastRefuelDetailsModel(
                    lastRefuelDistance = lastRefuelDistance,
                    lastRefuelFuelAverage = lastRefuelFuelAverage,
                    lastRefuelPayment = df.format(lastRefuelPayment).toDouble()
                )
            }
        }
        return if (refuelList.size == 1) {
            LastRefuelDetailsModel(
                lastRefuelDistance = 0,
                lastRefuelFuelAverage = 0.0,
                lastRefuelPayment = 0.0

            )
        } else {
            LastRefuelDetailsModel(
                lastRefuelDistance = 0,
                lastRefuelFuelAverage = 0.0,
                lastRefuelPayment = 0.0

            )
        }
    }
}


