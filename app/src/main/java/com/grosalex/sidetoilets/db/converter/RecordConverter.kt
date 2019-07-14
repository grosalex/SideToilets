package com.grosalex.sidetoilets.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.grosalex.sidetoilets.model.Field
import com.grosalex.sidetoilets.model.Geometry


class RecordConverter {
    @TypeConverter
    fun fieldFromString(value: String): Field {
        return Gson().fromJson(value, Field::class.java)
    }

    @TypeConverter
    fun fieldToString(field: Field): String {
        return Gson().toJson(field)
    }

    @TypeConverter
    fun geometryFromString(value: String): Geometry {
        return Gson().fromJson(value, Geometry::class.java)
    }

    @TypeConverter
    fun geometryToString(geometry: Geometry): String {
        return Gson().toJson(geometry)
    }
}