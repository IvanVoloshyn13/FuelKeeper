package com.example.fuelkeeper.di

import com.example.fuelkeeper.domain.repositoryInterface.AddNewRefuelRepository
import com.example.fuelkeeper.domain.repositoryInterface.AllRefuelLogRepository
import com.example.fuelkeeper.domain.repositoryInterface.EditRepository
import com.example.fuelkeeper.domain.repositoryInterface.HomeRepository
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideAddNewRefuelingUseCase(
        addNewRefuelRepository: AddNewRefuelRepository
    ): AddNewRefuelingUseCase {
        return AddNewRefuelingUseCase(addNewRefuelRepository)
    }

    @Provides
    @Singleton
    fun provideSetLocaleDateUseCase(
        addNewRefuelRepository: AddNewRefuelRepository
    ): SetLocaleDateUseCase {
        return SetLocaleDateUseCase(addNewRefuelRepository)
    }

    @Provides
    @Singleton
    fun provideEditLocaleDateUseCase(
        addNewRefuelRepository: AddNewRefuelRepository
    ): EditLocaleDateUseCase {
        return EditLocaleDateUseCase(addNewRefuelRepository)
    }

    @Provides
    @Singleton
    fun provideGetRefuelByIdUseCase(
        editRepository: EditRepository
    ): GetRefuelByIdUseCase {
        return GetRefuelByIdUseCase(editRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateRefuelUseCase(
        editRepository: EditRepository
    ): UpdateRefuelUseCase {
        return UpdateRefuelUseCase(editRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllTimeDrivingCostUseCase(
        homeRepository: HomeRepository
    ): GetAllTimeDrivingCostUseCase {
        return GetAllTimeDrivingCostUseCase(homeRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllTimeFuelAverageUseCase(
        homeRepository: HomeRepository
    ): GetAllTimeFuelAverageUseCase {
        return GetAllTimeFuelAverageUseCase(homeRepository)
    }

    @Provides
    @Singleton
    fun provideGetLastRefuelDetailUseCase(
        homeRepository: HomeRepository
    ): GetLastRefuelDetailUseCase {
        return GetLastRefuelDetailUseCase(homeRepository)
    }

    @Provides
    @Singleton
    fun provideGetSummaryRefuelStatsUseCase(
        homeRepository: HomeRepository
    ): GetSummaryRefuelStatsUseCase {
        return GetSummaryRefuelStatsUseCase(homeRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteRefuelLogUseCase(
        allRefuelLogRepository: AllRefuelLogRepository
    ): DeleteRefuelLogUseCase {
        return DeleteRefuelLogUseCase(allRefuelLogRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllRefuelLogUseCase(
        allRefuelLogRepository: AllRefuelLogRepository
    ): GetAllRefuelLogUseCase {
        return GetAllRefuelLogUseCase(allRefuelLogRepository)
    }

    @Provides
    @Singleton
    fun provideReturnDeletedElementUseCase(
        allRefuelLogRepository: AllRefuelLogRepository
    ): ReturnDeletedElementUseCase {
        return ReturnDeletedElementUseCase(allRefuelLogRepository)
    }

    @Provides
    @Singleton
    fun provideInsertDeletedRefuelItemUseCase(
        allRefuelLogRepository: AllRefuelLogRepository
    ): InsertDeletedRefuelItemUseCase {
        return InsertDeletedRefuelItemUseCase(allRefuelLogRepository)
    }

}