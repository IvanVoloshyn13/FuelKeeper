package com.example.fuelkeeper.domain.usecase.addNewRefuelFrag

import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import javax.inject.Inject


class SetLocaleDateUseCase @Inject constructor(private val refuelRepository: RefuelRepository) {

    fun setLocaleDate(): String {
        return refuelRepository.setLocaleDate()
    }
}