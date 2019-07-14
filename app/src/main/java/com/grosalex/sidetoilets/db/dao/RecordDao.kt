package com.grosalex.sidetoilets.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.grosalex.sidetoilets.model.Record
import androidx.room.OnConflictStrategy



@Dao
interface RecordDao {
    @Query("SELECT * FROM RECORD")
    suspend fun findAll(): List<Record>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecords(records: List<Record>)

    @Query("DELETE FROM RECORD")
    suspend fun deleteAll()
}