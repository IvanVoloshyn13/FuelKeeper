package com.example.fuelkeeper.data.repository

import com.example.fuelkeeper.data.models.RefuelingEntity
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDataBase
import com.example.fuelkeeper.domain.models.RefuelingModel
import javax.inject.Inject
import com.example.fuelkeeper.domain.repositoryInterface.EditRepository

class EditRepositoryImpl @Inject constructor(val db: RefuelingDataBase) : EditRepository {

    override suspend fun getRefuelById(id: Int): RefuelingModel {
        db.getRefuelingDao().getRefuelById(id).let { result ->
            if (result != null) {
                return RefuelingModel(
                    id=result.id,
                    refuelDate = result.refuelDate,
                    currentMileage = result.currentMileage,
                    fuelAmount = result.fuelAmount,
                    fuelPricePerLiter = result.fuelPricePerLiter,
                    fillUp = result.fillUp,
                    notes = result.notes
                )
            } else return RefuelingModel(
                refuelDate = "",
                currentMileage = 0,
                fuelAmount = 0.0,
                fuelPricePerLiter = 0.0,
                fillUp = false,
                notes = null
            )
        }
    }

    override suspend fun updateRefuel(refuel: RefuelingModel): Boolean {
        db.getRefuelingDao().updateRefuel(mapToRefuelingEntity(refuel))
        return true
    }

    private fun mapToRefuelingEntity(
        newRefuel: RefuelingModel
    ): RefuelingEntity {
            return RefuelingEntity(
                id=newRefuel.id,
                refuelDate = newRefuel.refuelDate,
                currentMileage = newRefuel.currentMileage,
                fuelAmount = newRefuel.fuelAmount,
                fuelPricePerLiter = newRefuel.fuelPricePerLiter,
                notes = newRefuel.notes,
                fillUp = newRefuel.fillUp
            )
        }

}