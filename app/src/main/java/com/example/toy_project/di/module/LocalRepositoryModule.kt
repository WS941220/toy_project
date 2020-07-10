package com.example.toy_project.di.module

import android.app.Application
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.util.AppExecutors
import com.example.android.architecture.blueprints.todoapp.util.DiskIOThreadExecutor
import com.example.toy_project.data.source.local.MemoDao
import com.example.toy_project.data.source.local.LocalDatabase
import com.example.toy_project.di.Scoped.AppScoped
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton


@Module
class LocalRepositoryModule {

    private val THREAD_COUNT = 3

    @AppScoped
    @Provides
    fun provideMemoDatabase(context: Application): LocalDatabase {
        return Room.databaseBuilder(context.applicationContext,
            LocalDatabase::class.java,
            "Local.db")
            .build()
    }

    @AppScoped
    @Provides
    fun provideMemoDao(localDatabase: LocalDatabase): MemoDao {
        return localDatabase.memoDao()
    }

    @AppScoped
    @Provides
    fun provideAppExecutors(): AppExecutors {
        return AppExecutors(
            DiskIOThreadExecutor(),
            Executors.newFixedThreadPool(THREAD_COUNT),
            AppExecutors.MainThreadExecutor()
        )
    }

//    @AppScoped
//    @Provides
//    fun memoRepository(memoDao: MemoDao): MemosDataSource {
//        return MemosRepository(memoDao)
//    }
}