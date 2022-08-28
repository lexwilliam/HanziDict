package com.lexwilliam.hanzidict.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.hanzidict.R
import com.lexwilliam.hanzidict.data.Hanzi
import com.lexwilliam.hanzidict.data.HanziRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val hanziRepository: HanziRepository
): ViewModel() {
    private var _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private var _isEmpty: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isEmpty = _isEmpty.asStateFlow()

    init {
        viewModelScope.launch {
            hanziRepository.isTableEmpty().collect{
                _isEmpty.value = it
                Log.d("MainViewModel", it.toString())
            }
        }
    }

    fun onListFull() {
        _isLoading.value = false
    }


    fun insertHanzi(
        item: Hanzi
    ) {
        viewModelScope.launch {
            hanziRepository.insertHanzi(item)
        }
    }

    fun readCsv(inputStream: InputStream) {

        val reader = BufferedReader(InputStreamReader(inputStream))
        try {
            var csvLine: String?
            while (reader.readLine().also { csvLine = it } != null) {
                if (csvLine != "") {
                    val row = csvLine!!.split(",", limit = 4).toTypedArray()
                    insertHanzi(Hanzi(row[0], row[1], row[2], row[3]))
                }
            }
        } catch (ex: IOException) {
            throw RuntimeException("Error in reading CSV file: $ex")
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                throw RuntimeException("Error while closing input stream: $e")
            }
        }
        _isLoading.value = false
    }
}