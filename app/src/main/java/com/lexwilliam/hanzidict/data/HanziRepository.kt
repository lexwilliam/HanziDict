package com.lexwilliam.hanzidict.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class HanziRepository @Inject constructor(
    private val hanziDao: HanziDao
) {
    suspend fun isTableEmpty(): Flow<Boolean> = flow {
        emit(hanziDao.getOneItemFromDatabase() == null)
    }

    suspend fun getSearchQuery(definition: String): Flow<List<Hanzi>> = flow {
        emit(hanziDao.getSearchQuery(definition).first())
    }


    suspend fun insertHanzi(hanzi: Hanzi) {
        Log.d("LOLZ", hanzi.toString())
        hanziDao.insertHanzi(hanzi)
    }
}