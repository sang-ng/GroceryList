package com.sanguyen.android.grocerylist.feature_shoppingitem.domain.use_case

import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.InvalidShoppingItemException
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.repository.ShoppingItemRepository

class UpdateShoppingItem(
    private val repository: ShoppingItemRepository
) {

    @Throws(InvalidShoppingItemException::class)
    suspend operator fun invoke(shoppingItem: ShoppingItem) {

        repository.updateShoppingItem(shoppingItem)
    }
}