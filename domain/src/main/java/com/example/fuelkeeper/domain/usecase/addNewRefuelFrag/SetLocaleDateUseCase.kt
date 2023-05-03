package com.example.fuelkeeper.domain.usecase.addNewRefuelFrag

import com.example.fuelkeeper.domain.repositoryInterface.AddNewRefuelRepository

class SetLocaleDateUseCase  (private val repository: AddNewRefuelRepository) {

    fun setLocaleDate(): String {
        return repository.setLocaleDate()
    }

}