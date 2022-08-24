package com.lexwilliam.hanzidict.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.hanzidict.data.Hanzi
import com.lexwilliam.hanzidict.data.HanziRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
}