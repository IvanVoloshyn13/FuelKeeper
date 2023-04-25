package com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail

import com.example.fuelkeeper.domain.repositoryInterface.AllRefuelLogRepository
import javax.inject.Inject

class DeleteRefuelLogUseCase @Inject constructor(private val allRefuelLogRepository: AllRefuelLogRepository) {
    suspend fun deleteRefuelLogById(id: Int) {
        allRefuelLogRepository.deleteRefuelLog(id = id)
    }
}