package com.example.fuelkeeper.domain.usecase.HomeFrag

import com.example.fuelkeeper.domain.models.RefuelingModel
import java.text.DecimalFormat
import javax.inject.Inject

class GetSummaryDrivingCostUseCase @Inject constructor(
    private val getAllTimeFuelAverageUseCase: GetAllTimeFuelAverageUseCase
) {
    private val df = DecimalFormat("#.##")
    suspend fun getAverageFuelPrice(
        refuelLogList: ArrayList<RefuelingModel>
    ): Double {
        val fuelAverage = getAllTimeFuelAverageUseCase.getAllTimeFuelAverage()
        var summaryPrice = 0.0
        for (elements in refuelLogList) {
            summaryPrice += elements.fuelPricePerLiter
        }
        val averageFuelPrice = summaryPrice / refuelLogList.size
        val averageDrivingCost = averageFuelPrice * fuelAverage
        return df.format(averageDrivingCost).toDouble()
    }
}