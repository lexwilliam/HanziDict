package com.lexwilliam.hanzidict.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hanzi_table")
data class Hanzi(
    @PrimaryKey
    val hanzi: String,
    val pinyin: String,
    val pinyinWithoutTone: String,
    val definition: String
)