package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.destinations.FavoritesScreenDestination
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.components.ShoppingListItem
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.util.UiEvent
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.util.components.GroceryBottomAppBar
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.util.components.SwipeToDismissBackground
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Destination(start = true)
@Composable
fun ShoppingItemsScreen(
    navigator: DestinationsNavigator,
    viewModel: ShoppingItemsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val titleState = viewModel.itemTitle.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            // reuse default SnackbarHost to have default animation and timing handling
            SnackbarHost(it) { data ->
                // custom snackbar with the custom colors
                Snackbar(
                    snackbarData = data
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Grocerylist") },
                actions = {
                    IconButton(onClick = {
                        navigator.navigate(FavoritesScreenDestination)
                    }) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites")
                    }
                }, elevation = 4.dp
            )
        },
        bottomBar = {
            GroceryBottomAppBar(
                text = titleState.text,
                hint = titleState.hint,
                onFocusChange = { viewModel.onEvent(ShoppingItemsEvent.ChangedTitleFocus(it)) },
                onValueChange = { viewModel.onEvent(ShoppingItemsEvent.EnteredTitle(it)) },
                isHintVisible = titleState.isHintVisible,
                onClick = { viewModel.onEvent(ShoppingItemsEvent.SaveItem) }
            )
        }
    ) {


        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.shoppingItems, key = { it.id!! }) { item ->

                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                            viewModel.onEvent(ShoppingItemsEvent.RemoveFromActualList(item))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Item deleted",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(ShoppingItemsEvent.RestoreShoppingItem)
                                }
                            }
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(0.6f) },
                    background = {

                        SwipeToDismissBackground(dismissState = dismissState)

                    },
                    dismissContent = {
                        ShoppingListItem(
                            shoppingItem = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colors.background),
                            onChecked = {
                                viewModel.onEvent(ShoppingItemsEvent.MarkShoppingItem(item))
                            }
                        )
                    }
                )
            }
        }
    }
}