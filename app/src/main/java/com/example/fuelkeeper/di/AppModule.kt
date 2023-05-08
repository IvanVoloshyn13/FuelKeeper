package com.example.fuelkeeper.di


import android.content.Context
import android.content.SharedPreferences
import com.example.fuelkeeper.data.repository.AddNewRefuelRepositoryImpl
import com.example.fuelkeeper.data.repository.AllRefuelLogRepositoryImpl
import com.example.fuelkeeper.data.repository.EditRepositoryImpl
import com.example.fuelkeeper.data.repository.HomeRepositoryImpl
import com.example.fuelkeeper.data.repository.SettingsRepositoryImpl
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDataBase
import com.example.fuelkeeper.domain.repositoryInterface.AddNewRefuelRepository
import com.example.fuelkeeper.domain.repositoryInterface.AllRefuelLogRepository
import com.example.fuelkeeper.domain.repositoryInterface.EditRepository
import com.example.fuelkeeper.domain.repositoryInterface.HomeRepository
import com.example.fuelkeeper.domain.repositoryInterface.SettingsRepository
import com.example.fuelkeeper.domain.usecase.addNewRefuelFrag.AddNewRefuelingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    fun provideSettingsRepositoryImpl(
        sharedPreferences: SharedPreferences
    ): SettingsRepository {
        return SettingsRepositoryImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideShredPrefStore(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
    }

    companion object {
        const val SHARED_PREF_KEY = "SharedKey"
    }


}