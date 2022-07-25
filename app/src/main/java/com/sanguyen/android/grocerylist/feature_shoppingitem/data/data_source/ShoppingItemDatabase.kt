package com.sanguyen.android.grocerylist.feature_shoppingitem.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ShoppingItemDatabase::class],
    version = 1
)
abstract class ShoppingItemDatabase : RoomDatabase(){

    abstract val shoppingItemDao : ShoppingItemDao

    companion object{
        const val DATABASE_NAME = "shoppingitems_db"
    }
}