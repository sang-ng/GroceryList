package com.sanguyen.android.grocerylist.feature_shoppingitem.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem

@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingItemDatabase : RoomDatabase() {

    abstract val shoppingItemDao: ShoppingItemDao

    companion object {
        const val DATABASE_NAME = "shoppingitems_db"
    }
}