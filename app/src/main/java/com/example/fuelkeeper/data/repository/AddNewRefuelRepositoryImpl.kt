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
) : AddNewRefuelRepository {

    override suspend fun addNewRefuelLog(
        newRefuel: RefuelingModel
    ): Boolean {
        val refuelingEntity = mapToRefuelingEntity(newRefuel)
        val rowId = db.getRefuelingDao().addNewRefuelData(refuelingEntity)
        return rowId > 0
    }

    private fun mapToRefuelingEntity(
        newRefuel: RefuelingModel
    ): RefuelingEntity {
            return RefuelingEntity(
                refuelDate = newRefuel.refuelDate,
                currentMileage = newRefuel.currentMileage,
                fuelAmount = newRefuel.fuelAmount,
                fuelPricePerLiter = newRefuel.fuelPricePerLiter,
                notes = newRefuel.notes,
                fillUp = newRefuel.fillUp
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


}
