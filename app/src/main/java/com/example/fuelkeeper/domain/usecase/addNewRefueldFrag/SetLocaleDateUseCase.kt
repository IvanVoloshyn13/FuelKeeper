package com.example.fuelkeeper.domain.usecase.addNewRefueldFrag

import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository


class SetLocaleDateUseCase(private val refuelRepository: RefuelRepository) {

    fun setLocaleDate(): String {
        return refuelRepository.setLocaleDate()
    }
}