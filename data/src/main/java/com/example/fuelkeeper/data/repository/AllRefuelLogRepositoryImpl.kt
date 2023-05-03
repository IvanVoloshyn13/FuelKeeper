package com.example.fuelkeeper.data.repository

import com.example.fuelkeeper.data.models.RefuelingEntity
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDataBase
import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.models.RefuelingStatModel
import com.example.fuelkeeper.domain.repositoryInterface.AllRefuelLogRepository
import javax.inject.Inject

const val CHUNK_SIZE = 3

class AllRefuelLogRepositoryImpl @Inject constructor(val db: RefuelingDataBase) :
    AllRefuelLogRepository {
    override suspend fun getAllRefuelLog(): Resource<List<RefuelingStatModel>> {
        val refuelLogList = db.getRefuelingDao().getAllRefuelLog()
        val refuelingStatModelList = mutableListOf<RefuelingStatModel>()
        if (refuelLogList.isNotEmpty()) {
            for (element in refuelLogList.indices) {
                val refuelingStatModel: RefuelingStatModel = if (element == 0) {
                    mapToRefuelingStatModel(
                        refuelLogList[element],
                        0,
                        0
                    )
                } else {
                    mapToRefuelingStatModel(
                        refuelLogList[element],
                        refuelLogList[element].currentMileage,
                        refuelLogList[element - 1].currentMileage
                    )
                }
                refuelingStatModelList.add(refuelingStatModel)
            }
        }
        return Resource.Success(refuelingStatModelList)
    }

    override suspend fun deleteRefuelLog(id: Int) {
        db.getRefuelingDao().deleteRefuel(id)
    }

    override suspend fun returnDeletedElement(itemId: Int): Resource<RefuelingModel> {
        db.getRefuelingDao().getRefuelById(itemId).let { refuelingEntity ->
            refuelingEntity.apply {
                val deletedRefuel = RefuelingModel(
                    id = id?.or(0),
                    refuelDate = refuelDate,
                    currentMileage = currentMileage,
                    fuelAmount = fuelAmount,
                    fuelPricePerLiter = fuelPricePerLiter,
                    notes = notes,
                    fillUp = fillUp

                )
                return Resource.Success(deletedRefuel)
            }
        }
    }

    override suspend fun insertDeletedElement(refuel: RefuelingModel) {
        val refuelEntity = RefuelingEntity(
            id = refuel.id,
            refuelDate = refuel.refuelDate,
            currentMileage = refuel.currentMileage,
            fuelAmount = refuel.fuelAmount,
            fuelPricePerLiter = refuel.fuelPricePerLiter,
            notes = refuel.notes,
            fillUp = refuel.fillUp
        )
        db.getRefuelingDao().addNewRefuelData(refuelEntity)
    }

    private fun mapToRefuelingStatModel(
        entity: RefuelingEntity,
        lastCurrentMileage: Int,
        previousCurrentMileage: Int
    ): RefuelingStatModel {
        val lastRefuelDistance = lastCurrentMileage - previousCurrentMileage
        if (lastRefuelDistance == 0) {
            return RefuelingStatModel(
                id = entity.id,
                refuelDate = entity.refuelDate,
                currentMileage = formatNumber(entity.currentMileage.toString(), CHUNK_SIZE),
                refuelPayment = formatDouble(entity.fuelAmount * entity.fuelPricePerLiter),
                lastRefuelDistance = lastRefuelDistance,
                fuelAverage = "0.0",
                notes = entity.notes,
                fillUp = entity.fillUp
            )
        } else {
            val fuelAverage = formatDouble(entity.fuelAmount / lastRefuelDistance * 100)
            return RefuelingStatModel(
                id = entity.id,
                refuelDate = entity.refuelDate,
                currentMileage = formatNumber(entity.currentMileage.toString(), CHUNK_SIZE),
                refuelPayment = formatDouble(entity.fuelAmount * entity.fuelPricePerLiter),
                lastRefuelDistance = lastRefuelDistance,
                fuelAverage = fuelAverage,
                notes = entity.notes,
                fillUp = entity.fillUp
            )
        }
    }


    private fun formatNumber(input: String, chunkSize: Int): String {
        val inputLength = input.length
        if (inputLength <= chunkSize) {
            return input
        }
        val reminder = inputLength % chunkSize
        val chunks =
            input.take(reminder).chunked(chunkSize) + input.drop(reminder).chunked(chunkSize)
        return chunks.joinToString(" ")
    }

    private fun formatDouble(number: Double): String {
        if (number != null) {
            return String.format("%.2f", number)
        } else return "0.0"
    }
}