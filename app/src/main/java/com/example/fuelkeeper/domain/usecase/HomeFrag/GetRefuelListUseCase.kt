package com.example.fuelkeeper.domain.usecase.HomeFrag

import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import javax.inject.Inject

class GetRefuelListUseCase @Inject constructor(private val repository: RefuelRepository) {

    suspend fun getRefuelList() = repository.getAllRefuelLog()
}