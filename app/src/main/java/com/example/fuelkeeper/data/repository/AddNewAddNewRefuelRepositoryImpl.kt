package com.example.fuelkeeper.data.repository

import com.example.fuelkeeper.data.models.RefuelingEntity
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDataBase
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.repositoryInterface.AddNewRefuelRepository
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

class AddNewRefuelRepositoryImpl @Inject constructor(
    private val db: RefuelingDataBase
) :
    AddNewRefuelRepository {


    //AddNewRefuelFragment
    override suspend fun addNewRefuelLog(
        newRefuel: RefuelingModel,
        lastRefuelMileage: Int
    ): Boolean {
        val refuelingEntity = mapToRefuelingEntity(newRefuel, lastRefuelMileage)
        val rowId = db.getRefuelingDao().addNewRefuelData(refuelingEntity)
        return rowId > 0
    }

    override suspend fun getLastRefuelCurrentMileage(): Int {
        var result: Int = try {
            db.getRefuelingDao().getLastCurrentMileage()
        } catch (e: Exception) {
            0
        }
        return result
    }


    override suspend fun getAllRefuelLog(): ArrayList<RefuelingModel> {
        TODO("Not yet implemented")
    }

    private fun mapToRefuelingEntity(
        newRefuel: RefuelingModel,
        lastRefuelMileage: Int
    ): RefuelingEntity {
        val refuelPayment = newRefuel.fuelAmount * newRefuel.fuelPricePerLiter
        if (lastRefuelMileage == 0) {
            return RefuelingEntity(
                refuelDate = newRefuel.refuelDate,
                currentMileage = newRefuel.currentMileage,
                fuelAmount = newRefuel.fuelAmount,
                fuelPricePerLiter = newRefuel.fuelPricePerLiter,
                refuelPayment = refuelPayment,
                lastRefuelDistance = 0,
                fuelAverage = 0.0,
                notes = newRefuel.notes,
                fillUp = newRefuel.fillUp
            )
        } else {
            val distanceOnLastRefuel = newRefuel.currentMileage - lastRefuelMileage
            val fuelAverage =
                formatDouble(newRefuel.fuelAmount / distanceOnLastRefuel * 100).toDouble()
            return RefuelingEntity(
                refuelDate = newRefuel.refuelDate,
                currentMileage = newRefuel.currentMileage,
                fuelAmount = newRefuel.fuelAmount,
                fuelPricePerLiter = newRefuel.fuelPricePerLiter,
                refuelPayment = refuelPayment,
                lastRefuelDistance = distanceOnLastRefuel,
                fuelAverage = fuelAverage,
                notes = newRefuel.notes,
                fillUp = newRefuel.fillUp
            )
        }
    }

    private fun mapToRefuelingModel(entity: RefuelingEntity): RefuelingModel {
        return RefuelingModel(
            refuelDate = entity.refuelDate,
            currentMileage = entity.currentMileage,
            fuelAmount = entity.fuelAmount,
            fuelPricePerLiter = entity.fuelPricePerLiter,
            notes = entity.notes,
            fillUp = entity.fillUp
        )
    }

    override fun setLocaleDate(): String {
        val locale = Locale.getDefault()
        val dateTime = DateFormat.getDateInstance(DateFormat.DEFAULT, locale)
        val calendar = Calendar.getInstance()
        val date = calendar.time
        val formattedDate = dateTime.format(date)
        return formattedDate
    }

    fun formatDouble(number: Double): String {
        if (number != null) {
            return String.format("%.2f", number)
        } else return "0.0"
    }

}
