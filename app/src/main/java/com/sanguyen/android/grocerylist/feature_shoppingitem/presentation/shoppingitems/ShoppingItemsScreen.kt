package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ShoppingItemsScreen(
    viewModel: ShoppingItemsViewModel = hiltViewModel()
) {
    val shopingItems = viewModel.shoppingItems.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
}