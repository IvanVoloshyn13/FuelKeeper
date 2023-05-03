package com.example.fuelkeeper.domain.usecase.editFragment

import com.example.fuelkeeper.domain.repositoryInterface.AddNewRefuelRepository

class EditLocaleDateUseCase  (private val repository: AddNewRefuelRepository) {
    fun editLocaleDate(): String {
        return repository.setLocaleDate()
    }

}