package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites

import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem

data class FavoritesListState(
    val isLoading: Boolean = false,
    var favorites: List<ShoppingItem> = emptyList()
) {
}