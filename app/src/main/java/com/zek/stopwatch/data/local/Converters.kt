package com.zek.stopwatch.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(value: String): List<Long> {
        return value.split(",").map { it.toLong() }
    }

    @TypeConverter
    fun fromList(list: List<Long>): String {
        return list.joinToString(",")
    }
}