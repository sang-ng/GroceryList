package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems

import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem

sealed class ShoppingItemsEvent {
    data class DeleteShoppingItem(val shoppingItem: ShoppingItem) : ShoppingItemsEvent()
    object RestoreShoppingItem : ShoppingItemsEvent()
}