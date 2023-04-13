package com.example.fuelkeeper.domain.usecase.HomeFrag

import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import java.text.DecimalFormat
import javax.inject.Inject

class GetAllTimeFuelAverageUseCase @Inject constructor(val repository: RefuelRepository) {

    suspend fun getAllTimeFuelAverage(): Double {
        val df = DecimalFormat("#.##")
        val fuelAmountList = repository.getFuelAmountLog()
        val currentMileage = repository.getCurrentMileageLog()
        var fuelAmountSum = 0.0
        if (fuelAmountList?.isNotEmpty() == true) {
            if (fuelAmountList.size > 1 && currentMileage.size > 1) {
                for (element in fuelAmountList) {
                    fuelAmountSum += element
                }
            }
            val distance = currentMileage.lastOrNull()?.minus(currentMileage[0])
            val allTimeFuelAmount = fuelAmountSum / distance!! * 100
            return df.format(allTimeFuelAmount).toDouble()
        } else return 0.0
    }

}