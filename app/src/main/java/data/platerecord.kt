package com.example.platechecker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plate_records")
data class PlateRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val plateNumber: String,
    val queryTime: Long,
    val isUsed: Boolean
)