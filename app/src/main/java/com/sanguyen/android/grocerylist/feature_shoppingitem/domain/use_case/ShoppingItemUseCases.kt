package com.sanguyen.android.grocerylist.feature_shoppingitem.domain.use_case

data class ShoppingItemUseCases(
    val getShoppingItems: GetShoppingItems,
    val deleteShoppingItem: DeleteShoppingItem,
    val addShoppingItem: AddShoppingItem,
    val getShoppingItemById: GetShoppingItemById
) 