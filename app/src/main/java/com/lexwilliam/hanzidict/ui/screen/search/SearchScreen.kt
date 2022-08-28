package com.lexwilliam.hanzidict.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lexwilliam.hanzidict.data.Hanzi

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onBackPressed: () -> Unit
) {
    val items = viewModel.hanziList.collectAsState().value
    val query = viewModel.query.collectAsState().value
    var hanzi by remember { mutableStateOf(Hanzi("", "", "", "") )}
    var dialogState by remember { mutableStateOf(false)}
    Column {
        SearchTopAppBar(
            query = query,
            onQueryChange = { viewModel.search(it) },
            onBackPressed = onBackPressed
        )
        if (query != "") {
            SearchResultList(
                items = items,
                onClick = {
                    dialogState = true
                    hanzi = it
                }
            )
        }
    }
    if(dialogState) {
        HanziDialog(hanzi = hanzi, dialogState = dialogState, onDialogStateChange = { dialogState = it })
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
    var cursorColor  by remember { mutableStateOf(Color.Black) }
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

@Composable
fun SearchResultList(
    items: List<Hanzi>,
    onClick: (Hanzi) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        items(items = items) { item ->
            SearchResult(item = item, onClick = onClick)
            Divider()
        }
        item {
            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun SearchResult(
    item: Hanzi,
    onClick: (Hanzi) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable { onClick(item) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = item.hanzi, style = MaterialTheme.typography.headlineSmall)
        Column {
            Text(text = item.pinyin, style = MaterialTheme.typography.titleMedium)
            Text(text = item.definition, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun HanziDialog(
    hanzi: Hanzi,
    dialogState: Boolean,
    onDialogStateChange: (Boolean) -> Unit
) {

    if (dialogState) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                onDialogStateChange(false)
            },
            title = {
                Text(text = "${hanzi.hanzi} ${hanzi.pinyin}", style = MaterialTheme.typography.headlineLarge)
            },
            text = {
                Text(text = hanzi.definition, style = MaterialTheme.typography.bodyLarge)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDialogStateChange(false)
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}