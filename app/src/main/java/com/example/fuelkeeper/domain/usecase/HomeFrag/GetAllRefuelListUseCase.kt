package com.example.fuelkeeper.domain.usecase.HomeFrag

import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import javax.inject.Inject

class GetAllRefuelListUseCase @Inject constructor(private val repository: RefuelRepository) {

    suspend fun getAllRefuelList() = repository.getAllRefuelLog()
}