package com.sanguyen.android.grocerylist.feature_shoppingitem.data.data_source

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {

    @Query("SELECT * FROM")
    fun getShoppingItems() : Flow<List<ShoppingItem>>
}