package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.util

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()

}
