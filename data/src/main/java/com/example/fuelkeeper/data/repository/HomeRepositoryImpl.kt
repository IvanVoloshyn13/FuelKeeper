package com.example.fuelkeeper.data.repository

import com.example.fuelkeeper.data.source.localeStorage.RefuelingDataBase
import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.LastRefuelDetailsModel
import com.example.fuelkeeper.domain.models.SummaryRefuelStatModel
import com.example.fuelkeeper.domain.repositoryInterface.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    val db: RefuelingDataBase
) : HomeRepository {

    override suspend fun getLastRefuelDetail(): Resource<LastRefuelDetailsModel> {
        val lastRefuel = db.getRefuelingDao().getLastRefuel()
        val secondLastRefuel = db.getRefuelingDao().getSecondLastRefuelItem()
        if (lastRefuel != null && secondLastRefuel != null) {
            val lastRefuelDistance = lastRefuel.currentMileage - secondLastRefuel.currentMileage
            val lastRefuelFuelAverage =
                formatDouble(lastRefuel.fuelAmount / lastRefuelDistance * 100).toDouble()
            val lastRefuelDetail = LastRefuelDetailsModel(
                lastRefuelDistance = lastRefuelDistance,
                lastRefuelFuelAverage = lastRefuelFuelAverage,
                lastRefuelPayment = formatDouble(lastRefuel.fuelAmount * lastRefuel.fuelPricePerLiter).toDouble()
            )
            return Resource.Success(lastRefuelDetail)
        } else if (lastRefuel != null && secondLastRefuel == null) {
            val lastRefuelDistance = 0
            val lastRefuelFuelAverage = 0.0
            val lastRefuelDetail = LastRefuelDetailsModel(
                lastRefuelDistance = lastRefuelDistance,
                lastRefuelFuelAverage = lastRefuelFuelAverage,
                lastRefuelPayment = formatDouble(lastRefuel.fuelAmount * lastRefuel.fuelPricePerLiter).toDouble()
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

    override suspend fun getSummaryRefuelStatDetail(): Resource<SummaryRefuelStatModel> =
        withContext(Dispatchers.IO) {
            val refuelAmountSum = getSummaryFuelAmount()
            val refuelPaymentsSum = getSummaryPayments()
            val summaryDistance = getSummaryDistance()
            val summaryStat = SummaryRefuelStatModel(
                summaryPayments = refuelPaymentsSum,
                summaryDistance = summaryDistance,
                summaryFuel = refuelAmountSum
            )
            return@withContext Resource.Success(summaryStat)

        }

    override suspend fun getAllTimeFuelAverage(): Resource<Double> = withContext(Dispatchers.IO) {
        if (getSummaryDistance() != 0 && db.getRefuelingDao()
                .getRefuelingRegisterTableCount() > 2
        ) {
            val fuelAmountSum = getFuelAmountSum() - getFirstFuelAmount()
            val allTimeFuelAverage =
                formatDouble(fuelAmountSum / getSummaryDistance() * 100).toDouble()
            return@withContext Resource.Success(allTimeFuelAverage)
        } else if (getSummaryDistance() != 0 && db.getRefuelingDao()
                .getRefuelingRegisterTableCount() == 2
        ) {
            return@withContext Resource.Success(getLastRefuelDetail().data?.lastRefuelFuelAverage)
        } else return@withContext Resource.Success(0.0)
    }

    override suspend fun getAllTimeDrivingCost(): Resource<Double> =
        withContext(Dispatchers.IO) {
            val allTimeFuelAverage = getAllTimeFuelAverage().data
            val allTimeFuelPricePerLiterAverage = getAllTimeFuelPricePerLiterAverage()
            if (allTimeFuelAverage != null) {
                val allTimeDrivingCost =
                    formatDouble(allTimeFuelAverage * allTimeFuelPricePerLiterAverage).toDouble()
                return@withContext Resource.Success(allTimeDrivingCost)
            } else return@withContext Resource.Success(0.0)
        }

    private suspend fun getFirstFuelAmount(): Double {
        return db.getRefuelingDao().getFirstFuelAmount()
    }

    private suspend fun getAllTimeFuelPricePerLiterAverage(): Double {
        if (db.getRefuelingDao().getSumFuelPricePerLiter() != null) {
            val count = db.getRefuelingDao().getRefuelingRegisterTableCount()
            val fuelPricePerLiterSum = db.getRefuelingDao().getSumFuelPricePerLiter()
            return formatDouble(fuelPricePerLiterSum / count).toDouble()
        } else return 0.0
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

    private suspend fun getFuelAmountSum(): Double {
        db.getRefuelingDao().getSumFuelAmount().let { sumFuelAmount ->
            if (sumFuelAmount != null)
                return formatDouble(sumFuelAmount).toDouble()
            else return 0.0
        }
    }

    private suspend fun getSummaryPayments(): Double {
        val allRefuelLog = db.getRefuelingDao().getAllRefuelLog()
        var allTimeDrivingCost = 0.0
        allRefuelLog.let { refuelList ->
            if (refuelList.isNotEmpty()) {
                for (element in refuelList.indices) {
                    val refuelPayment =
                        refuelList[element].fuelAmount * refuelList[element].fuelPricePerLiter
                    allTimeDrivingCost += refuelPayment
                }
                return formatDouble(allTimeDrivingCost).toDouble()
            } else return 0.0
        }
    }

    private suspend fun getSummaryFuelAmount(): Double {
        return if (db.getRefuelingDao().getSumRefuelAmount() == null)
            0.0
        else formatDouble(db.getRefuelingDao().getSumRefuelAmount()).toDouble()
    }

    private fun formatDouble(number: Double): String {
        if (number != null) {
            return String.format("%.2f", number)
        } else return "0.0"
    }
}