package com.lexwilliam.hanzidict.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.lexwilliam.hanzidict.R
import com.lexwilliam.hanzidict.data.Hanzi
import com.lexwilliam.hanzidict.ui.theme.HanziDictTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.isEmpty.collect { isEmpty ->
                if (isEmpty != null) {
                    if (isEmpty) {
                        val inputStream: InputStream =
                            resources.openRawResource(R.raw.chinese_dictionary_table)
                        viewModel.readCsv(inputStream)
                    } else {
                        viewModel.onListFull()
                    }
                }
            }
        }
        setContent {
            val state = viewModel.isLoading.collectAsState()
            HanziDictTheme {
                if (!state.value) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        HanziDictApp()
                    }
                }
            }
        }
    }
}