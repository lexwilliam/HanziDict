package com.lexwilliam.hanzidict.di

import android.content.Context
import androidx.room.Room
import com.lexwilliam.hanzidict.data.HanziDao
import com.lexwilliam.hanzidict.data.HanziDatabase
import com.lexwilliam.hanzidict.data.HanziRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideHanziDb(@ApplicationContext context: Context): HanziDatabase {
        return Room
            .databaseBuilder(
                context,
                HanziDatabase::class.java,
                "hanzi_database"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideHanziDAO(hanziDatabase: HanziDatabase): HanziDao {
        return hanziDatabase.hanziDao()
    }

    @Provides
    @Singleton
    fun provideHanziRepository(
        hanziDao: HanziDao
    ): HanziRepository =
        HanziRepository(hanziDao)
}