package com.example.fuelkeeper.di

import android.content.Context
import android.content.SharedPreferences
import com.example.fuelkeeper.data.repository.SettingsRepositoryImpl
import com.example.fuelkeeper.domain.repositoryInterface.AddNewRefuelRepository
import com.example.fuelkeeper.domain.repositoryInterface.AllRefuelLogRepository
import com.example.fuelkeeper.domain.repositoryInterface.EditRepository
import com.example.fuelkeeper.domain.repositoryInterface.HomeRepository
import com.example.fuelkeeper.domain.repositoryInterface.SettingsRepository
import com.example.fuelkeeper.domain.usecase.addNewRefuelFrag.AddNewRefuelingUseCase
import com.example.fuelkeeper.domain.usecase.addNewRefuelFrag.SetLocaleDateUseCase
import com.example.fuelkeeper.domain.usecase.editFragment.EditLocaleDateUseCase
import com.example.fuelkeeper.domain.usecase.editFragment.GetRefuelByIdUseCase
import com.example.fuelkeeper.domain.usecase.editFragment.UpdateRefuelUseCase
import com.example.fuelkeeper.domain.usecase.homeFrag.GetAllTimeDrivingCostUseCase
import com.example.fuelkeeper.domain.usecase.homeFrag.GetAllTimeFuelAverageUseCase
import com.example.fuelkeeper.domain.usecase.homeFrag.GetLastRefuelDetailUseCase
import com.example.fuelkeeper.domain.usecase.homeFrag.GetSummaryRefuelStatsUseCase
import com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail.DeleteRefuelLogUseCase
import com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail.GetAllRefuelLogUseCase
import com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail.InsertDeletedRefuelItemUseCase
import com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail.ReturnDeletedElementUseCase
import com.example.fuelkeeper.domain.usecase.settingsFrag.GetIsNightModeUseCase
import com.example.fuelkeeper.domain.usecase.settingsFrag.SaveIsNightModeStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideAddNewRefuelingUseCase(
        addNewRefuelRepository: AddNewRefuelRepository
    ): AddNewRefuelingUseCase {
        return AddNewRefuelingUseCase(addNewRefuelRepository)
    }

    @Provides
    fun provideSetLocaleDateUseCase(
        addNewRefuelRepository: AddNewRefuelRepository
    ): SetLocaleDateUseCase {
        return SetLocaleDateUseCase(addNewRefuelRepository)
    }

    @Provides
    fun provideEditLocaleDateUseCase(
        addNewRefuelRepository: AddNewRefuelRepository
    ): EditLocaleDateUseCase {
        return EditLocaleDateUseCase(addNewRefuelRepository)
    }

    @Provides
    fun provideGetRefuelByIdUseCase(
        editRepository: EditRepository
    ): GetRefuelByIdUseCase {
        return GetRefuelByIdUseCase(editRepository)
    }

    @Provides
    fun provideUpdateRefuelUseCase(
        editRepository: EditRepository
    ): UpdateRefuelUseCase {
        return UpdateRefuelUseCase(editRepository)
    }

    @Provides
    fun provideGetAllTimeDrivingCostUseCase(
        homeRepository: HomeRepository
    ): GetAllTimeDrivingCostUseCase {
        return GetAllTimeDrivingCostUseCase(homeRepository)
    }

    @Provides
    fun provideGetAllTimeFuelAverageUseCase(
        homeRepository: HomeRepository
    ): GetAllTimeFuelAverageUseCase {
        return GetAllTimeFuelAverageUseCase(homeRepository)
    }

    @Provides
    fun provideGetLastRefuelDetailUseCase(
        homeRepository: HomeRepository
    ): GetLastRefuelDetailUseCase {
        return GetLastRefuelDetailUseCase(homeRepository)
    }

    @Provides
    fun provideGetSummaryRefuelStatsUseCase(
        homeRepository: HomeRepository
    ): GetSummaryRefuelStatsUseCase {
        return GetSummaryRefuelStatsUseCase(homeRepository)
    }

    @Provides
    fun provideDeleteRefuelLogUseCase(
        allRefuelLogRepository: AllRefuelLogRepository
    ): DeleteRefuelLogUseCase {
        return DeleteRefuelLogUseCase(allRefuelLogRepository)
    }

    @Provides
    fun provideGetAllRefuelLogUseCase(
        allRefuelLogRepository: AllRefuelLogRepository
    ): GetAllRefuelLogUseCase {
        return GetAllRefuelLogUseCase(allRefuelLogRepository)
    }

    @Provides
    fun provideReturnDeletedElementUseCase(
        allRefuelLogRepository: AllRefuelLogRepository
    ): ReturnDeletedElementUseCase {
        return ReturnDeletedElementUseCase(allRefuelLogRepository)
    }

    @Provides
    fun provideInsertDeletedRefuelItemUseCase(
        allRefuelLogRepository: AllRefuelLogRepository
    ): InsertDeletedRefuelItemUseCase {
        return InsertDeletedRefuelItemUseCase(allRefuelLogRepository)
    }

    @Provides
    fun provideSaveNightModeStatusUseCase(
        settingsRepository: SettingsRepository
    ): SaveIsNightModeStatusUseCase {
        return SaveIsNightModeStatusUseCase(settingsRepository)
    }

    @Provides
    fun provideGetNightModeUseCase(
        settingsRepository: SettingsRepository
    ): GetIsNightModeUseCase {
        return GetIsNightModeUseCase(settingsRepository)
    }

}