package com.example.fuelkeeper.data.repository

import com.example.fuelkeeper.data.models.RefuelingStatEntity
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDataBase
import com.example.fuelkeeper.domain.models.LastRefuelDetailsModel
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.models.RefuelingStatModel
import com.example.fuelkeeper.domain.repositoryInterface.RefuelStatisticsRepository
import javax.inject.Inject

class RefuelStatisticsRepositoryRepositoryImpl @Inject constructor(private val db: RefuelingDataBase) :
    RefuelStatisticsRepository {

    override suspend fun addNewRefuelStat(
        newRefuel: RefuelingModel,
        newRefuelStat: LastRefuelDetailsModel
    ): Boolean {
        if (newRefuelStat.lastRefuelFuelAverage != null) {
            val refuelStatEntity = RefuelingStatEntity(
                refuelDate = newRefuel.refuelDate,
                currentMileage = newRefuel.currentMileage,
                refuelPayment = newRefuelStat.lastRefuelPayment,
                lastRefuelDistance = newRefuelStat.lastRefuelDistance,
                fuelAverage = newRefuelStat.lastRefuelFuelAverage,
                notes = newRefuel.notes,
                fillUp = newRefuel.fillUp
            )
            val rowId = db.getRefuelingStatDao().addNewRefuelingStat(refuelStatEntity)
            return rowId > 0
        } else {
            val refuelStatEntity = RefuelingStatEntity(
                refuelDate = newRefuel.refuelDate,
                currentMileage = newRefuel.currentMileage,
                refuelPayment = 0.0,
                lastRefuelDistance = 0,
                fuelAverage = 0.0,
                notes = newRefuel.notes,
                fillUp = newRefuel.fillUp
            )
            val rowId = db.getRefuelingStatDao().addNewRefuelingStat(refuelStatEntity)
            return rowId > 0
            return false
        }
    }

    override suspend fun getAllRefuelStat(): ArrayList<RefuelingStatModel> {
        return db.getRefuelingStatDao().getAllRefuelStatList()
            .map { mapToRefuelingStatModel(it) } as ArrayList
    }

    private fun mapToRefuelingStatModel(entity: RefuelingStatEntity): RefuelingStatModel {
        return RefuelingStatModel(
            refuelDate = entity.refuelDate,
            refuelPayment = entity.refuelPayment,
            lastRefuelDistance = entity.lastRefuelDistance,
            currentMileage = entity.currentMileage,
            fuelAverage = entity.fuelAverage,
            fillUp = entity.fillUp,
            notes = entity.notes
        )
    }


}