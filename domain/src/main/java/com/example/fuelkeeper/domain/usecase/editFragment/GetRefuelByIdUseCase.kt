package com.example.fuelkeeper.domain.usecase.editFragment
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.domain.repositoryInterface.EditRepository
class GetRefuelByIdUseCase  (private val editRepository: EditRepository) {

    suspend fun getRefuelById(itemId: Int): RefuelingModel? {
        return editRepository.getRefuelById(itemId)
    }
}