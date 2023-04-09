package com.example.fuelkeeper.data.source.localeStorage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fuelkeeper.domain.models.RefuelingEntity

@Dao
interface RefuelingDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewRefuelData(refuel: RefuelingEntity): Long

    @Query("SELECT * FROM refueling_register")
    suspend fun getRefuelingList(): List<RefuelingEntity>

    @Query("DELETE FROM refueling_register WHERE id=:id")
    suspend fun deleteRefuel(id: Int)

    @Query("SELECT fuelAmount FROM refueling_register ")
    suspend fun getFuelAmountLog(): List<Double>
}

