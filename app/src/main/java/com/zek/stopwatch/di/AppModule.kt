package com.zek.stopwatch.di

import android.content.Context
import androidx.room.Room
import com.zek.stopwatch.data.StopWatchDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStopWatchDataBase(
        @ApplicationContext context: Context
    ): StopWatchDataBase=
        Room.databaseBuilder(
            context,
            StopWatchDataBase::class.java,
            "stop_watch_database"
        ).build()

    @Provides
    @Singleton
    fun provideStopWatchDoa(
        db: StopWatchDataBase
    ) = db.getStopWatchDoa()

}