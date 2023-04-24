package com.example.fuelkeeper.data.source.localeStorage

import androidx.room.*
import com.example.fuelkeeper.data.models.RefuelingEntity

@Dao
interface RefuelingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewRefuelData(refuel: RefuelingEntity): Long

    @Query("SELECT * FROM refueling_register ")
    suspend fun getAllRefuelLog(): List<RefuelingEntity>

    @Query("SELECT currentMileage FROM refueling_register ORDER BY currentMileage DESC LIMIT 1")
    suspend fun getLastCurrentMileage(): Int

    @Query("SELECT currentMileage FROM refueling_register ORDER BY currentMileage ASC LIMIT 1")
    suspend fun getFirstCurrentMileage(): Int

    @Query("SELECT currentMileage FROM refueling_register ORDER BY id DESC LIMIT 1 OFFSET 1")
    suspend fun getSecondLastCurrentMileage(): Int

    @Query("SELECT SUM(fuelAmount) FROM refueling_register")
    suspend fun getSumRefuelAmount(): Double

    @Query("SELECT * FROM refueling_register ORDER BY currentMileage DESC LIMIT 1")
    suspend fun getLastRefuel(): RefuelingEntity

    @Query("SELECT COUNT(*) FROM refueling_register")
    suspend fun getRefuelingRegisterTableCount(): Int

    @Query("SELECT SUM(fuelPricePerLiter) FROM refueling_register")
    suspend fun getSumFuelPricePerLiter(): Double

    @Query("SELECT SUM(fuelAmount) FROM refueling_register")
    suspend fun getSumFuelAmount(): Double?

    @Query("SELECT fuelAmount FROM refueling_register ORDER BY id ASC LIMIT 1")
    suspend fun getFirstFuelAmount(): Double


    @Query("DELETE FROM refueling_register WHERE currentMileage=:id")
    suspend fun deleteRefuel(id: Int)

    @Query("SELECT * FROM refueling_register WHERE id=:itemId LIMIT 1")
    suspend fun getRefuelById(itemId: Int): RefuelingEntity

    @Update
    suspend fun updateRefuel(refuel: RefuelingEntity): Int

    @Query("SELECT * FROM refueling_register ORDER BY id DESC LIMIT 1 OFFSET 1")
    suspend fun getSecondLastRefuelItem(): RefuelingEntity


}

