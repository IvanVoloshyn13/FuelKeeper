package com.example.fuelkeeper.data.source.localeStorage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fuelkeeper.data.models.RefuelingEntity

@Database(entities = [RefuelingEntity::class], version = 1, exportSchema = false)
abstract class RefuelingDataBase : RoomDatabase() {
    abstract fun getRefuelingDao(): RefuelingDao
}