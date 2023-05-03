package com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail
import com.example.fuelkeeper.domain.repositoryInterface.AllRefuelLogRepository

class DeleteRefuelLogUseCase (private val allRefuelLogRepository: AllRefuelLogRepository) {
    suspend fun deleteRefuelLogById(id: Int) {
        allRefuelLogRepository.deleteRefuelLog(id = id)
    }
}