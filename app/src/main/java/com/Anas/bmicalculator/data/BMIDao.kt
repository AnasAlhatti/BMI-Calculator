package com.Anas.bmicalculator.data

import androidx.room.*
import com.Anas.bmicalculator.model.BMIRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface BMIDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: BMIRecord)

    @Delete
    suspend fun delete(record: BMIRecord)

    @Query("DELETE FROM bmi_history")
    suspend fun deleteAll()

    @Query("SELECT * FROM bmi_history ORDER BY timestamp DESC")
    fun getAllRecords(): Flow<List<BMIRecord>>
}
