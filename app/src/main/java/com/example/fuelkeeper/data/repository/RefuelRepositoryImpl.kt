package com.example.fuelkeeper.data.repository

import androidx.lifecycle.LiveData
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDao
import com.example.fuelkeeper.domain.models.RefuelingEntity
import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import javax.inject.Inject

class RefuelRepositoryImpl @Inject constructor(private val refuelingDao: RefuelingDao) :
    RefuelRepository {
    override suspend fun getAllRefuelLog(): LiveData<List<RefuelingEntity>> {
        return refuelingDao.getRefuelingList()
    }

    override suspend fun addNewRefuelLog(refuel: RefuelingEntity): Boolean {
        val rowId = refuelingDao.addNewRefuelData(refuel)
        return rowId > 0
    }
}