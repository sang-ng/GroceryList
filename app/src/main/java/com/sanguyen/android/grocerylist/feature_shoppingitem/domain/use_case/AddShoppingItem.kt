package com.sanguyen.android.grocerylist.feature_shoppingitem.domain.use_case

import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.InvalidShoppingItemException
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.repository.ShoppingItemRepository

class AddShoppingItem(
    private val repository: ShoppingItemRepository
) {

    @Throws(InvalidShoppingItemException::class)
    suspend operator fun invoke(shoppingItem: ShoppingItem) {
        if (shoppingItem.title.isBlank()) {
            throw InvalidShoppingItemException("This field cannot be empty.")
        }

        repository.insertShoppingItem(shoppingItem)
    }
}