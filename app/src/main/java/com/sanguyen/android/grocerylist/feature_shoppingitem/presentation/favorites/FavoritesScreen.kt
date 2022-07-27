package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites.components.FavoriteListItem
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.components.TransparentHintTextField
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.util.UiEvent
import com.sanguyen.android.grocerylist.ui.theme.LightRed
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
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Favorites")
                    }
                }, elevation = 4.dp
            )
        },
        bottomBar = {
            BottomAppBar() {
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
                            viewModel.onEvent(FavoritesEvents.EnteredTitle(it))
                        },
                        onFocusChange = {
                            viewModel.onEvent(FavoritesEvents.ChangedTitleFocus(it))
                        },
                        isHintVisible = titleState.isHintVisible,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.h6.copy(
                            color = Color.White
                        )
                    )
                    IconButton(
                        onClick = {
                            viewModel.onEvent(FavoritesEvents.SaveItem)
                        }) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Save",
                            tint = Color.White
                        )
                    }
                }
            }
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
                                    println("Clicked")
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

                        val color by animateColorAsState(
                            targetValue = if (dismissState.targetValue == DismissValue.Default) LightRed else Color.Red
                        )

                        val scale by animateFloatAsState(
                            targetValue =
                            if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Icon",
                                modifier = Modifier
                                    .scale(scale)
                                    .padding(16.dp)
                                ,
                                tint = Color.White
                            )
                        }
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