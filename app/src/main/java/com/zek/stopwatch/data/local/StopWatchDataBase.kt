package com.zek.stopwatch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zek.stopwatch.data.entities.StopWatchRecord

@Database(entities = [StopWatchRecord::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class StopWatchDataBase: RoomDatabase() {

    abstract fun getStopWatchDoa(): StopWatchDoa
}