package com.example.fuelkeeper.data.source.localeStorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fuelkeeper.data.models.RefuelingEntity
import com.example.fuelkeeper.domain.Resource

@Dao
interface RefuelingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewRefuelData(refuel: RefuelingEntity): Long

    @Query("SELECT currentMileage FROM refueling_register ORDER BY ID DESC LIMIT 1")
    suspend fun getLastCurrentMileage(): Int

    @Query("SELECT currentMileage FROM refueling_register ORDER BY ID ASC LIMIT 1")
    suspend fun getFirstCurrentMileage(): Int

    @Query("SELECT SUM(refuelPayment) FROM refueling_register ")
    suspend fun getSumRefuelPayments(): Double

    @Query("SELECT SUM(fuelAmount) FROM refueling_register")
    suspend fun getSumRefuelAmount(): Double

    @Query("SELECT * FROM refueling_register ORDER BY ID DESC LIMIT 1")
    suspend fun getLastRefuel(): RefuelingEntity

    @Query("SELECT COUNT(*) FROM refueling_register")
    suspend fun getRefuelingRegisterTableCount(): Int

    @Query("SELECT SUM(fuelAverage) FROM refueling_register")
    suspend fun getSumFuelAverage(): Double


    @Query("DELETE FROM refueling_register WHERE id=:id")
    suspend fun deleteRefuel(id: Int)

}

