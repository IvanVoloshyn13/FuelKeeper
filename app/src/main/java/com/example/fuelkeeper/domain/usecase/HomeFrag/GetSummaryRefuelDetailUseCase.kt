package com.example.fuelkeeper.domain.usecase.HomeFrag

import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.models.SummaryRefuelLogModel
import java.text.DecimalFormat

class GetSummaryRefuelDetailUseCase {
    private val df = DecimalFormat("#.##")

    fun getSummaryRefuelDetails(refuelLogList: ArrayList<RefuelingModel>): SummaryRefuelLogModel {
        val summaryDistance = getSummaryDistance(refuelLogList)
        val summaryPayment = getSummaryOfPayments(refuelLogList)
        val summaryFuel = getSummaryFuel(refuelLogList)
        return SummaryRefuelLogModel(
            summaryDistance = summaryDistance,
            summaryPayments = summaryPayment,
            summaryFuel = summaryFuel
        )
    }

    private fun getSummaryDistance(refuelLogList: ArrayList<RefuelingModel>): Int {
        val firstRefuelDistance = refuelLogList.firstOrNull()?.currentMileage
        val lastRefuelDistance = refuelLogList.lastOrNull()?.currentMileage
        if (lastRefuelDistance != null && firstRefuelDistance != null) {
            return lastRefuelDistance - firstRefuelDistance
        } else return 0
    }

    private fun getSummaryOfPayments(refuelLogList: ArrayList<RefuelingModel>): Double {
        var sum = 0.0
        if (refuelLogList.isNotEmpty()) {
            for (elements in refuelLogList) {
                sum += (elements.fuelPricePerLiter * elements.fuelAmount)

            }
        }
        return df.format(sum).toDouble()
    }

    private fun getSummaryFuel(refuelLogList: ArrayList<RefuelingModel>): Double {
        var sum = 0.0
        if (refuelLogList.isNotEmpty()) {
            for (element in refuelLogList) {
                sum += element.fuelAmount
            }
        }
        return df.format(sum).toDouble()
    }
}