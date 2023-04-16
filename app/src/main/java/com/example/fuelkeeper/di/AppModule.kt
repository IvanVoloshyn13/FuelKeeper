package com.example.fuelkeeper.di

import com.example.fuelkeeper.data.repository.RefuelRepositoryImpl
import com.example.fuelkeeper.data.repository.RefuelStatisticsRepositoryRepositoryImpl
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDataBase
import com.example.fuelkeeper.domain.repositoryInterface.RefuelRepository
import com.example.fuelkeeper.domain.repositoryInterface.RefuelStatisticsRepository
import com.example.fuelkeeper.domain.usecase.HomeFrag.GetLastRefuelDetailUseCase
import com.example.fuelkeeper.domain.usecase.HomeFrag.GetSummaryRefuelDetailUseCase
import com.example.fuelkeeper.domain.usecase.addNewRefuelFrag.AddNewRefuelingUseCase
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
    fun provideRefuelStatRepositoryImpl(dataBase: RefuelingDataBase): RefuelStatisticsRepository =
        RefuelStatisticsRepositoryRepositoryImpl(db = dataBase)


    @Provides
    @Singleton
    fun provideAddNewRefuelingUseCase(refuelRepository: RefuelRepository) =
        AddNewRefuelingUseCase(refuelRepository = refuelRepository)

    @Provides
    @Singleton
    fun provideGetLastRefuelDetailUseCase() = GetLastRefuelDetailUseCase()

    @Provides
    @Singleton
    fun provideGetSummaryRefuelDetailUseCase() = GetSummaryRefuelDetailUseCase()
}