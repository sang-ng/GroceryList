package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites.components.FavoriteListItem
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.util.UiEvent
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.util.components.GroceryBottomAppBar
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.util.components.SwipeToDismissBackground
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun FavoritesScreen(
    navigator: DestinationsNavigator,
    viewModel: FavoritesViewModel = hiltViewModel()
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
            SnackbarHost(it) { data ->
                Snackbar(
                    snackbarData = data
                )
            }
        },
        topBar = {

            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")

                    }
                },
                title = { Text("Favorites") },
                elevation = 4.dp
            )
        },
        bottomBar = {
            GroceryBottomAppBar(
                text = titleState.text,
                hint = titleState.hint,
                onFocusChange = { viewModel.onEvent(FavoritesEvents.ChangedTitleFocus(it)) },
                onValueChange = { viewModel.onEvent(FavoritesEvents.EnteredTitle(it)) },
                isHintVisible = titleState.isHintVisible,
                onClick = { viewModel.onEvent(FavoritesEvents.SaveItem) }
            )
        }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(state.favorites, key = { it.id!! }) { item ->

                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                            viewModel.onEvent(FavoritesEvents.RemoveFromFavorites(item))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Item deleted",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(FavoritesEvents.RestoreFavorite(item))
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
                        FavoriteListItem(
                            shoppingItem = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            onClicked = { viewModel.onEvent(FavoritesEvents.AddToActualList(item)) }
                        )
                    }
                )
            }
        }
    }
}