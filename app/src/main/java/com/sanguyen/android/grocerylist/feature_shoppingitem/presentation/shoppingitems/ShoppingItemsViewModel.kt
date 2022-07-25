package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.use_case.ShoppingItemUseCases
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.components.ItemTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingItemsViewModel @Inject constructor(
    private val useCases: ShoppingItemUseCases
) : ViewModel() {

    private val _shoppingItems = mutableStateOf(ShoppingItemListState())
    val shoppingItems = _shoppingItems

    private var recentlyDeletedItem: ShoppingItem? = null

    private var getShoppingItemsJob: Job? = null

    private val _itemTitle = mutableStateOf(
        ItemTextFieldState(
            hint = "Enter new item.."
        )
    )
    val itemTitle: State<ItemTextFieldState> = _itemTitle

    init {
        getShoppingItems()
    }

    fun onEvent(event: ShoppingItemsEvent) {
        when (event) {
            is ShoppingItemsEvent.EnteredTitle -> {
                _itemTitle.value = itemTitle.value.copy(
                    text = event.value
                )
            }
            is ShoppingItemsEvent.ChangedTitleFocus -> {
                _itemTitle.value = itemTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && itemTitle.value.text.isBlank()
                )
            }
        }
    }

    private fun getShoppingItems() {
        getShoppingItemsJob?.cancel()

        getShoppingItemsJob = useCases.getShoppingItems()
            .onEach { items ->
                _shoppingItems.value = shoppingItems.value.copy(
                    shoppingItems = items
                )
            }.launchIn(viewModelScope)
    }
}