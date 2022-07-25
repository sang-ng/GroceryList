package com.sanguyen.android.grocerylist.feature_shoppingitem.data.data_source

import androidx.room.*
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {

    @Query("SELECT * FROM ShoppingItem")
    fun getShoppingItems(): Flow<List<ShoppingItem>>

    @Query("SELECT * FROM ShoppingItem WHERE id = :id")
    suspend fun getNoteById(id: Int): ShoppingItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
}