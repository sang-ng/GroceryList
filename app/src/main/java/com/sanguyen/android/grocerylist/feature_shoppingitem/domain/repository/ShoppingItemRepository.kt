package com.sanguyen.android.grocerylist.feature_shoppingitem.domain.repository

import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingItemRepository {

    fun getShoppingItems(): Flow<List<ShoppingItem>>
    suspend fun getShoppingItemById(id: Int): ShoppingItem?
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
}