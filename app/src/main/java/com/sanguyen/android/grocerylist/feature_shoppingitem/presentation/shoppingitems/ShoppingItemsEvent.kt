package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems

import androidx.compose.ui.focus.FocusState
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites.FavoritesEvents

sealed class ShoppingItemsEvent {
    data class EnteredTitle(val value: String) : ShoppingItemsEvent()
    data class ChangedTitleFocus(val focusState: FocusState) : ShoppingItemsEvent()
    data class DeleteShoppingItem(val shoppingItem: ShoppingItem) : ShoppingItemsEvent()
    object SaveItem : ShoppingItemsEvent()
    object RestoreShoppingItem : ShoppingItemsEvent()
    data class MarkShoppingItem(val shoppingItem: ShoppingItem) : ShoppingItemsEvent()
    data class RemoveFromActualList(val shoppingItem: ShoppingItem) : ShoppingItemsEvent()

}