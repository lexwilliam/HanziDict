package com.lexwilliam.hanzidict.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class HanziRepository @Inject constructor(
    private val hanziDao: HanziDao
) {
    suspend fun isTableEmpty(): Flow<Boolean> = flow {
        emit(hanziDao.getOneItemFromDatabase() == null)
    }

    suspend fun getSearchQuery(q: String): Flow<List<Hanzi>> = flow {
        val first = hanziDao.getSearchQuery(q).first()
        val second = hanziDao.getSearcyQueryForDefinition(q).first()

        emit(first + second)
    }


    suspend fun insertHanzi(hanzi: Hanzi) {
        Log.d("LOLZ", hanzi.toString())
        hanziDao.insertHanzi(hanzi)
    }
}