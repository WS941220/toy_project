package com.example.toy_project.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.toy_project.di.model.Memo

@Database(entities = [Memo::class], version = 1)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun memoDao(): MemoDao

}