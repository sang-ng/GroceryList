package com.sanguyen.android.grocerylist.feature_shoppingitem.domain.use_case

import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.repository.ShoppingItemRepository
import kotlinx.coroutines.flow.Flow

class GetShoppingItems(
    private val repository: ShoppingItemRepository
) {

    operator fun invoke(): Flow<List<ShoppingItem>> {
        return repository.getShoppingItems()
    }
}