package com.example.fuelkeeper.data.repository

import com.example.fuelkeeper.data.source.localeStorage.RefuelingDataBase
import com.example.fuelkeeper.data.models.RefuelingEntity
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import java.text.DateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class RefuelRepositoryImpl @Inject constructor(private val db: RefuelingDataBase) :
    RefuelRepository {

    override suspend fun getAllRefuelLog(): ArrayList<RefuelingEntity> {
        return db.getRefuelingDao().getRefuelingList() as ArrayList<RefuelingEntity>
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


    override fun setLocaleDate(): String {
        val locale = Locale.getDefault()
        val dateTime = DateFormat.getDateInstance(DateFormat.DEFAULT, locale)
        val calendar = Calendar.getInstance()
        val date = calendar.time
        val formattedDate = dateTime.format(date)
        return formattedDate
    }
}