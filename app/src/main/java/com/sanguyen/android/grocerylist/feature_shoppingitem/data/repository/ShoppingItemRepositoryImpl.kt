package com.sanguyen.android.grocerylist.feature_shoppingitem.data.repository

import com.sanguyen.android.grocerylist.feature_shoppingitem.data.data_source.ShoppingItemDao
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.repository.ShoppingItemRepository
import kotlinx.coroutines.flow.Flow

class ShoppingItemRepositoryImpl(
    private val dao: ShoppingItemDao
) : ShoppingItemRepository {
    override fun getShoppingItems(): Flow<List<ShoppingItem>> {
        return dao.getShoppingItems()
    }

    override suspend fun getShoppingItemById(id: Int): ShoppingItem? {
        return dao.getShoppingItemById(id)
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        dao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        dao.deleteShoppingItem(shoppingItem)
    }

    override suspend fun updateShoppingItem(shoppingItem: ShoppingItem) {
        dao.updateShoppingItem(shoppingItem)
    }
}