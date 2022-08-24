package com.lexwilliam.hanzidict.ui.screen.search

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
class SearchViewModel @Inject constructor(
    private val hanziRepository: HanziRepository
): ViewModel() {

    private var _hanziList = MutableStateFlow(emptyList<Hanzi>())
    val hanziList = _hanziList.asStateFlow()

    private var _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun search(query: String) {
        _query.value = query
        if (query != "") {
            viewModelScope.launch {
                hanziRepository.getSearchQuery(query).collect {
                    _hanziList.value = it
                    Log.d("SearchViewModel", it.toString())
                }
            }
        }
    }
}

