package com.example.fuelkeeper.di

import android.content.Context
import androidx.room.Room
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDao
import com.example.fuelkeeper.data.source.localeStorage.RefuelingDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val REFUELING_DATABASE_NAME="refueling_log.db"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RefuelingDataBase {
        return Room.databaseBuilder(context, RefuelingDataBase::class.java, REFUELING_DATABASE_NAME)
            .build()
    }


}