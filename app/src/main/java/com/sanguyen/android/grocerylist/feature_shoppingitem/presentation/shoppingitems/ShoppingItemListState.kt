package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems

import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem

data class ShoppingItemListState(
    val isLoading: Boolean = false,
    val shoppingItems: List<ShoppingItem> = emptyList()
) {
}