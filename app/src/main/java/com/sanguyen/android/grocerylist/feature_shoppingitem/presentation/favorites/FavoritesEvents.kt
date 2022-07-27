package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites

import androidx.compose.ui.focus.FocusState
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem

sealed class FavoritesEvents {
    data class EnteredTitle(val value: String) : FavoritesEvents()
    data class ChangedTitleFocus(val focusState: FocusState) : FavoritesEvents()
    data class DeleteShoppingItem(val shoppingItem: ShoppingItem) : FavoritesEvents()
    object SaveItem : FavoritesEvents()
    object RestoreShoppingItem : FavoritesEvents()
}