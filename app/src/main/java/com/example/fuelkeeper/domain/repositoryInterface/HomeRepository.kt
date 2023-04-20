package com.example.fuelkeeper.domain.repositoryInterface

import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.LastRefuelDetailsModel
import com.example.fuelkeeper.domain.models.SummaryRefuelStatModel

interface HomeRepository {
    suspend fun getLastRefuelDetail(): Resource<LastRefuelDetailsModel>
    suspend fun getSummaryRefuelStatDetail(): Resource<SummaryRefuelStatModel>
    suspend fun getAllTimeFuelAverage(): Resource<Double>
}

