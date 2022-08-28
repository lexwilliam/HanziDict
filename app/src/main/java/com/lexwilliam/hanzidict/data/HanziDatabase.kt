package com.lexwilliam.hanzidict.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Hanzi::class],
    version = 2,
    exportSchema = false)
abstract class HanziDatabase: RoomDatabase() {
    abstract fun hanziDao(): HanziDao
}