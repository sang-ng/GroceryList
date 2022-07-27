package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites.components

import androidx.lifecycle.ViewModel
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.use_case.ShoppingItemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val useCases: ShoppingItemUseCases
) : ViewModel() {



}
