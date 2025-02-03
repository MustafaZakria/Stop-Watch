package com.zek.stopwatch.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zek.stopwatch.data.entities.StopWatchRecord

@Database(entities = [StopWatchRecord::class], version = 1)
@TypeConverters(Converters::class)
abstract class StopWatchDataBase: RoomDatabase() {

    abstract fun getStopWatchDoa(): StopWatchDoa
}