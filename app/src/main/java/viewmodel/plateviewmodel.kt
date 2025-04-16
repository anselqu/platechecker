package com.example.platechecker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.platechecker.data.PlateDatabase
import com.example.platechecker.data.PlateRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PlateViewModel(application: Application) : AndroidViewModel(application) {
    private val plateDao = PlateDatabase.getDatabase(application).plateDao()
    val allRecords: Flow<List<PlateRecord>> = plateDao.getAllRecords()
    private val _queryResult = MutableLiveData<String>()
    val queryResult: LiveData<String> = _queryResult

    fun queryPlate(plateNumber: String) {
        viewModelScope.launch {
            val record = plateDao.getRecordByPlate(plateNumber)
            if (record == null) {
                val newRecord = PlateRecord(
                    plateNumber = plateNumber,
                    queryTime = System.currentTimeMillis(),
                    isUsed = false
                )
                plateDao.insert(newRecord)
                _queryResult.value = "新车牌（未使用）"
            } else {
                _queryResult.value = if (record.isUsed) "已使用" else "未使用"
            }
        }
    }

    fun markAsUsed(plateNumber: String) {
        viewModelScope.launch {
            val record = plateDao.getRecordByPlate(plateNumber)
            if (record != null && !record.isUsed) {
                plateDao.update(record.copy(isUsed = true))
                _queryResult.value = "已标记为使用"
            }
        }
    }
}