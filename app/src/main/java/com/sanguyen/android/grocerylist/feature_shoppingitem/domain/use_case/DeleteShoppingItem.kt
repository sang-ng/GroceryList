package com.sanguyen.android.grocerylist.feature_shoppingitem.domain.use_case

import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.repository.ShoppingItemRepository

class DeleteShoppingItem(
    private val repository: ShoppingItemRepository
) {

    suspend operator fun invoke(shoppingItem: ShoppingItem) {
        repository.deleteShoppingItem(shoppingItem)
    }
}