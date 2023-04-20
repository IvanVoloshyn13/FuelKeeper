package com.example.fuelkeeper.domain.usecase.addNewRefuelFrag

import com.example.fuelkeeper.domain.repositoryInterface.AddNewRefuelRepository
import javax.inject.Inject

class GetLastRefuelCurrentMileageUseCase @Inject constructor(val repository: AddNewRefuelRepository) {
    suspend fun getLastRefuelCurrentMileage(): Int {
        return repository.getLastRefuelCurrentMileage()
    }
}