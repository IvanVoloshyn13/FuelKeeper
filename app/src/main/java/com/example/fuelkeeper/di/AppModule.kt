package com.example.fuelkeeper.di

import com.example.fuelkeeper.data.repository.RefuelRepositoryImpl
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDataBase
import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import com.example.fuelkeeper.domain.usecase.HomeFrag.GetFuelAmountLogUseCase
import com.example.fuelkeeper.domain.usecase.addNewRefueldFrag.AddNewRefuelingUseCase
import com.example.fuelkeeper.domain.usecase.addNewRefueldFrag.SetLocaleDateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRefuelRepositoryImpl(dataBase: RefuelingDataBase): RefuelRepository =
        RefuelRepositoryImpl(db = dataBase)

    @Provides
    @Singleton
    fun provideSetLocaleDateUseCase(refuelRepository: RefuelRepository) =
        SetLocaleDateUseCase(refuelRepository)

    @Provides
    @Singleton
    fun provideAddNewRefuelingUseCase(refuelRepository: RefuelRepository) =
        AddNewRefuelingUseCase(refuelRepository = refuelRepository)

    @Provides
    @Singleton
    fun provideFuelAmountLogUseCAse(refuelRepository: RefuelRepository) =
        GetFuelAmountLogUseCase(refuelRepository = refuelRepository)
}