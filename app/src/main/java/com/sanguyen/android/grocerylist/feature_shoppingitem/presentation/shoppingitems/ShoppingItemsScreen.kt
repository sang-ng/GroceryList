package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ShoppingItemsScreen(
    viewModel: ShoppingItemsViewModel = hiltViewModel()
) {
    val shopingItems = viewModel.shoppingItems.value
    val titleState = viewModel.itemTitle.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ShoppingItemsViewModel.UiEvent.SaveNote -> {
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Grocerylist") },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites")
                    }
                }, elevation = 4.dp
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TransparentHintTextField(
                        modifier = Modifier.padding(start = 8.dp),
                        text = titleState.text,
                        hint = titleState.hint,
                        onValueChange = {
                            viewModel.onEvent(ShoppingItemsEvent.EnteredTitle(it))
                        },
                        onFocusChange = {
                            viewModel.onEvent(ShoppingItemsEvent.ChangedTitleFocus(it))
                        },
                        isHintVisible = titleState.isHintVisible,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.h6.copy(
                            color = Color.White
                        )
                    )
                    IconButton(
                        onClick = { viewModel.onEvent(ShoppingItemsEvent.SaveItem) }) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Save",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    ) {

    }
}