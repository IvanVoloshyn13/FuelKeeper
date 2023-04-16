package com.example.fuelkeeper.data.repository

import com.example.fuelkeeper.data.models.RefuelingEntity
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDataBase
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

class RefuelRepositoryImpl @Inject constructor(private val db: RefuelingDataBase) :
    RefuelRepository {

    override suspend fun getAllRefuelLog(): ArrayList<RefuelingModel> {
        return db.getRefuelingDao().getRefuelingList().map { mapToRefuelingModel(it) } as ArrayList
    }

    override suspend fun addNewRefuelLog(refuel: RefuelingModel): Boolean {
        val refuelingEntity = RefuelingEntity(
            refuelDate = refuel.refuelDate,
            currentMileage = refuel.currentMileage,
            fuelAmount = refuel.fuelAmount,
            fuelPricePerLiter = refuel.fuelPricePerLiter,
            notes = refuel.notes,
            fillUp = refuel.fillUp
        )
        val rowId = db.getRefuelingDao().addNewRefuelData(refuelingEntity)
        return rowId > 0
    }

    override suspend fun getFuelAmountLog(): List<Double> {
        return db.getRefuelingDao().getFuelAmountLog()
    }

    override suspend fun getCurrentMileageLog(): List<Int> {
        return db.getRefuelingDao().getCurrentMileageLog()
    }


    override fun setLocaleDate(): String {
        val locale = Locale.getDefault()
        val dateTime = DateFormat.getDateInstance(DateFormat.DEFAULT, locale)
        val calendar = Calendar.getInstance()
        val date = calendar.time
        val formattedDate = dateTime.format(date)
        return formattedDate
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
}