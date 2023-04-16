package com.example.fuelkeeper.data.source.localeStorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fuelkeeper.data.models.RefuelingStatEntity

@Dao
interface RefuelingStatDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewRefuelingStat(newRefuel: RefuelingStatEntity): Long

    @Query("SELECT * FROM refueling_stat")
    suspend fun getAllRefuelStatList(): List<RefuelingStatEntity>

}