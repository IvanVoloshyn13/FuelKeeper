package com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail

import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.RefuelingStatModel
import com.example.fuelkeeper.domain.repositoryInterface.AllRefuelLogRepository
import javax.inject.Inject

class GetAllRefuelLogUseCase @Inject constructor(private val allRefuelLogRepository: AllRefuelLogRepository) {
    suspend fun getAllRefuelLog(): Resource<List<RefuelingStatModel>> {
        return allRefuelLogRepository.getAllRefuelLog()
    }
}