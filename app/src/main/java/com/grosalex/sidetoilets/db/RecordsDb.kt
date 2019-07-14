package com.grosalex.sidetoilets.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.grosalex.sidetoilets.db.converter.RecordConverter
import com.grosalex.sidetoilets.db.dao.RecordDao
import com.grosalex.sidetoilets.model.Record

@Database(
    entities = [
        (Record::class)
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecordConverter::class)
abstract class RecordsDb : RoomDatabase() {
    abstract fun recordDao(): RecordDao

    companion object {
        private var INSTANCE: RecordsDb? = null
        private const val DB_NAME = "record.db"

        fun getInstance(context: Context): RecordsDb {
            INSTANCE?.let { return it }

            val db = Room.databaseBuilder(context.applicationContext, RecordsDb::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

            INSTANCE = db
            return db
        }
    }
}