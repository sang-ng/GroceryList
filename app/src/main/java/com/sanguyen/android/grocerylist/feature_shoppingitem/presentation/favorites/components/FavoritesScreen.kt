package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.ShoppingItemsEvent
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.ShoppingItemsViewModel
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.components.TransparentHintTextField

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        TopAppBar(
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
                        text = "titleState.text",
                        hint = "titleState.hint",
                        onValueChange = {
                        },
                        onFocusChange = {

                        },
                        isHintVisible = false,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.h6.copy(
                            color = Color.White
                        )
                    )
                    IconButton(
                        onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Save",
                            tint = Color.White
                        )
                    }
                }
            }
        }) {

    }
}