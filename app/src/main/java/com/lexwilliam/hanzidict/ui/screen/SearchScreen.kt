package com.lexwilliam.hanzidict.ui.screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.lexwilliam.hanzidict.ui.theme.HanziDictTheme

@Composable
fun SearchScreen(
    onBackPressed: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    Column {
        SearchTopAppBar(
            query = query,
            onQueryChange = { query = it },
            onBackPressed = onBackPressed
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchTopAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var cursorColor  by remember { mutableStateOf(Color.Transparent) }
    val focusRequester = FocusRequester()
    val keyboard = LocalSoftwareKeyboardController.current
    val isFocused by interactionSource.collectIsPressedAsState()

    if(isFocused) {
        cursorColor = Color.Black
    }
    SmallTopAppBar(
        title = {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .focusRequester(focusRequester),
                value = query,
                onValueChange = { onQueryChange(it) },
                singleLine = true,
                cursorBrush = SolidColor(cursorColor),
                interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboard?.hide()
                        cursorColor = Color.Transparent
                    }
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        }
    )
    if(query == "") {
        DisposableEffect(Unit) {
            focusRequester.requestFocus()
            onDispose { }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    HanziDictTheme {
        SearchScreen(onBackPressed = {})
    }
}