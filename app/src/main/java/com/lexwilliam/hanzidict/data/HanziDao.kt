package com.lexwilliam.hanzidict.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HanziDao {

    @Query("SELECT * FROM hanzi_table LIMIT 1")
    suspend fun getOneItemFromDatabase(): Hanzi?

    @Query("SELECT * FROM hanzi_table WHERE hanzi == :q OR pinyinWithoutTone LIKE :q || '%'")
    fun getSearchQuery(q: String): Flow<List<Hanzi>>

    @Query("SELECT * FROM hanzi_table WHERE definition LIKE '%' || :q || '%'")
    fun getSearcyQueryForDefinition(q: String): Flow<List<Hanzi>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHanzi(hanzi: Hanzi)

}