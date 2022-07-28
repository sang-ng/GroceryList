package com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingItem(
    val title: String,
    var isFavorite: Boolean = false,
    var isMarked: Boolean = false,
    var isActual: Boolean = false,
    @PrimaryKey val id: Int? = null
) {

}

class InvalidShoppingItemException(message: String) : Exception(message)