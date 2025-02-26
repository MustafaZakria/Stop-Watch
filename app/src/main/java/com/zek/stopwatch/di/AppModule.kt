package com.zek.stopwatch.di

import android.content.Context
import androidx.room.Room
import com.zek.stopwatch.data.local.IStopWatchDoa
import com.zek.stopwatch.data.local.StopWatchDataBase
import com.zek.stopwatch.repository.IMainRepository
import com.zek.stopwatch.repository.MainRepository
import com.zek.stopwatch.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStopWatchDataBase(
        @ApplicationContext context: Context
    ): StopWatchDataBase =
        Room.databaseBuilder(
            context,
            StopWatchDataBase::class.java,
            "stop_watch_database"
        ).build()

    @Provides
    @Singleton
    fun provideStopWatchDoa(
        db: StopWatchDataBase
    ): IStopWatchDoa = db.getStopWatchDoa()

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = object : DispatcherProvider {
        override val io = Dispatchers.IO
        override val main = Dispatchers.Main
        override val unconfined = Dispatchers.Unconfined
        override val default = Dispatchers.Default
    }

    @Provides
    @Singleton
    fun provideMainRepository(
        stopWatchDoa: IStopWatchDoa,
        dispatcherProvider: DispatcherProvider
    ): IMainRepository = MainRepository(
        stopWatchDoa = stopWatchDoa,
        dispatcherProvider = dispatcherProvider
    )

}