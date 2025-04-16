package com.example.platechecker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlateDao {
    @Insert
    suspend fun insert(record: PlateRecord)

    @Update
    suspend fun update(record: PlateRecord)

    @Query("SELECT * FROM plate_records WHERE plateNumber = :plateNumber LIMIT 1")
    suspend fun getRecordByPlate(plateNumber: String): PlateRecord?

    @Query("SELECT * FROM plate_records ORDER BY queryTime DESC")
    fun getAllRecords(): Flow<List<PlateRecord>>
}