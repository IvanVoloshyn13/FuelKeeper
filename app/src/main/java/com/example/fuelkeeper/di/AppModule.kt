package com.example.fuelkeeper.di


import com.example.fuelkeeper.data.repository.AddNewRefuelRepositoryImpl
import com.example.fuelkeeper.data.repository.AllRefuelLogRepositoryImpl
import com.example.fuelkeeper.data.repository.EditRepositoryImpl
import com.example.fuelkeeper.data.repository.HomeRepositoryImpl
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDataBase
import com.example.fuelkeeper.domain.repositoryInterface.AddNewRefuelRepository
import com.example.fuelkeeper.domain.repositoryInterface.AllRefuelLogRepository
import com.example.fuelkeeper.domain.repositoryInterface.EditRepository
import com.example.fuelkeeper.domain.repositoryInterface.HomeRepository
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
    fun provideRefuelRepositoryImpl(
        dataBase: RefuelingDataBase
    ): AddNewRefuelRepository {
        return AddNewRefuelRepositoryImpl(dataBase)
    }

    @Provides
    @Singleton
    fun provideHomeRepositoryImpl(
        dataBase: RefuelingDataBase
    ): HomeRepository {
        return HomeRepositoryImpl(dataBase)
    }

    @Provides
    @Singleton
    fun provideAllRefuelLogRepositoryImpl(
        dataBase: RefuelingDataBase
    ): AllRefuelLogRepository {
        return AllRefuelLogRepositoryImpl(dataBase)
    }

    @Provides
    @Singleton
    fun provideEditRepositoryRepositoryImpl(
        dataBase: RefuelingDataBase
    ): EditRepository {
        return EditRepositoryImpl(dataBase)
    }


}