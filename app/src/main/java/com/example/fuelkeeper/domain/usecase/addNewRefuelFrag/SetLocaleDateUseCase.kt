package com.example.fuelkeeper.domain.usecase.addNewRefuelFrag

import com.example.fuelkeeper.domain.repositoryInterface.AddNewRefuelRepository
import javax.inject.Inject


class SetLocaleDateUseCase @Inject constructor(private val repository: AddNewRefuelRepository) {

    fun setLocaleDate(): String {
        return repository.setLocaleDate()
    }
}