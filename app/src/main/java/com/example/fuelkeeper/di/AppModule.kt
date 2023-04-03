package com.example.fuelkeeper.di

import com.example.fuelkeeper.data.repository.RefuelRepositoryImpl
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDao
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
    fun provideRefuelRepositoryImpl(refuelDao: RefuelingDao) = RefuelRepositoryImpl(refuelDao)
}