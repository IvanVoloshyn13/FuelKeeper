package com.example.fuelkeeper.data.repository

import com.example.fuelkeeper.data.source.localeStorage.RefuelingDataBase
import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.LastRefuelDetailsModel
import com.example.fuelkeeper.domain.models.SummaryRefuelStatModel
import com.example.fuelkeeper.domain.repositoryInterface.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    val db: RefuelingDataBase
) : HomeRepository {

    override suspend fun getLastRefuelDetail(): Resource<LastRefuelDetailsModel> {
        val lastRefuel = db.getRefuelingDao().getLastRefuel()
        if (lastRefuel != null) {
            val lastRefuelFuelAverage =
                formatDouble(lastRefuel.fuelAmount / lastRefuel.lastRefuelDistance * 100).toDouble()
            val lastRefuelDetail = LastRefuelDetailsModel(
                lastRefuelDistance = lastRefuel.lastRefuelDistance,
                lastRefuelFuelAverage = lastRefuelFuelAverage,
                lastRefuelPayment = formatDouble(lastRefuel.refuelPayment).toDouble()
            )
            return Resource.Success(lastRefuelDetail)
        } else {
            return Resource.Error(
                message = "Your refuel logs are empty . Add first element to see your stats",
                data = LastRefuelDetailsModel(
                    lastRefuelDistance = 0,
                    lastRefuelPayment = 0.0,
                    lastRefuelFuelAverage = 0.0
                )
            )
        }
    }

    override suspend fun getSummaryRefuelStatDetail(): Resource<SummaryRefuelStatModel> {
        val refuelAmountSum = getSummaryFuelAmount()
        val refuelPaymentsSum = getSummaryPayments()
        val summaryDistance = getSummaryDistance()
        val summaryStat = SummaryRefuelStatModel(
            summaryPayments = refuelPaymentsSum,
            summaryDistance = summaryDistance,
            summaryFuel = refuelAmountSum
        )
        return Resource.Success(summaryStat)

    }

    override suspend fun getAllTimeFuelAverage(): Resource<Double> {
        if (db.getRefuelingDao().getRefuelingRegisterTableCount() != null && db.getRefuelingDao()
                .getSumFuelAverage() != null
        ) {
            val tableSize = db.getRefuelingDao().getRefuelingRegisterTableCount()
            val fuelAverageSum = db.getRefuelingDao().getSumFuelAverage()
            return Resource.Success(fuelAverageSum / tableSize)
        } else return Resource.Success(0.0)
    }


    private suspend fun getSummaryDistance(): Int {
        return if (db.getRefuelingDao().getFirstCurrentMileage() != null && db.getRefuelingDao()
                .getLastCurrentMileage() != null
        ) {
            val firstMileage = db.getRefuelingDao().getFirstCurrentMileage()
            val lastMileage = db.getRefuelingDao().getLastCurrentMileage()
            if (firstMileage == lastMileage) {
                0
            } else
                lastMileage - firstMileage
        } else 0


    }

    private suspend fun getSummaryPayments(): Double {
        return if (db.getRefuelingDao().getSumRefuelPayments() == null)
            0.0
        else
            db.getRefuelingDao().getSumRefuelPayments()
    }

    private suspend fun getSummaryFuelAmount(): Double {
        return if (db.getRefuelingDao().getSumRefuelAmount() == null)
            0.0
        else db.getRefuelingDao().getSumRefuelAmount()
    }

    fun formatDouble(number: Double): String {
        if (number != null) {
            return String.format("%.2f", number)
        } else return "0.0"
    }
}