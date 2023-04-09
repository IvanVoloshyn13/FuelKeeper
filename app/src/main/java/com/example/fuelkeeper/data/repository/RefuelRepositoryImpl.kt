package com.example.fuelkeeper.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDao
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDataBase
import com.example.fuelkeeper.domain.models.RefuelingEntity
import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import java.text.DateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class RefuelRepositoryImpl @Inject constructor(private val db: RefuelingDataBase) :
    RefuelRepository {

    override var allRefuelList = ArrayList<RefuelingEntity>()

    override suspend fun getAllRefuelLog() {
        allRefuelList = db.getRefuelingDao().getRefuelingList() as ArrayList<RefuelingEntity>
    }

    override suspend fun addNewRefuelLog(refuel: RefuelingEntity): Boolean {
        val rowId = db.getRefuelingDao().addNewRefuelData(refuel)
        return rowId > 0
    }

    override suspend fun getFuelAmountLog(): List<Double> {
        return db.getRefuelingDao().getFuelAmountLog()
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