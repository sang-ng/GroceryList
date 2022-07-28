package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.InvalidShoppingItemException
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.use_case.ShoppingItemUseCases
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.components.ItemTextFieldState
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingItemsViewModel @Inject constructor(
    private val useCases: ShoppingItemUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ShoppingItemListState())
    val state = _state

    private var recentlyRemovedItem: ShoppingItem? = null

    private var getShoppingItemsJob: Job? = null

    private val _itemTitle = mutableStateOf(
        ItemTextFieldState(
            hint = "Enter new item.."
        )
    )
    val itemTitle: State<ItemTextFieldState> = _itemTitle

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


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
            is ShoppingItemsEvent.SaveItem -> {
                viewModelScope.launch {
                    try {
                        useCases.addShoppingItem(
                            ShoppingItem(
                                title = itemTitle.value.text,
                                isFavorite = false,
                                isMarked = false,
                                isActual = true
                            )
                        )
                        _itemTitle.value = itemTitle.value.copy(
                            text = ""
                        )
                    } catch (e: InvalidShoppingItemException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save item"
                            )
                        )
                    }
                }
            }
            is ShoppingItemsEvent.DeleteShoppingItem -> {
                viewModelScope.launch {
                    useCases.deleteShoppingItem(event.shoppingItem)
                    recentlyRemovedItem = event.shoppingItem
                }
            }
            is ShoppingItemsEvent.MarkShoppingItem -> {
                viewModelScope.launch {

                    event.shoppingItem.isMarked = !event.shoppingItem.isMarked
                    useCases.updateShoppingItem(event.shoppingItem)

                    refreshList(shoppingItem = event.shoppingItem, toMap = "isMarked")
                }
            }
            is ShoppingItemsEvent.RemoveFromActualList -> {
                viewModelScope.launch {

                    event.shoppingItem.isActual = false
                    useCases.updateShoppingItem(event.shoppingItem)

                    recentlyRemovedItem = event.shoppingItem

                    refreshList(shoppingItem = event.shoppingItem, toMap = "isActual")
                }
            }
            is ShoppingItemsEvent.RestoreShoppingItem -> {
                viewModelScope.launch {
                    recentlyRemovedItem?.isActual = true
                    recentlyRemovedItem?.let { useCases.addShoppingItem(it) }
                    recentlyRemovedItem = null
                }
            }
        }
    }

    private fun refreshList(shoppingItem: ShoppingItem, toMap: String) {
        if (toMap == "isMarked") {
            _state.value.shoppingItems =
                _state.value.shoppingItems.map { if (it.id == shoppingItem.id) it.copy(isMarked = !it.isMarked) else it }
        }
        if (toMap == "isActual") {
            _state.value.shoppingItems =
                _state.value.shoppingItems.map { if (it.id == shoppingItem.id) it.copy(isActual = !it.isActual) else it }
        }
    }

    private fun getShoppingItems() {
        getShoppingItemsJob?.cancel()

        getShoppingItemsJob = useCases.getShoppingItems()

            .onEach { items ->
                cleanShoppingItemDb(list = items)
                _state.value = state.value.copy(
                    shoppingItems = items.filter { it.isActual }
                )
            }.launchIn(viewModelScope)
    }

    private suspend fun cleanShoppingItemDb(list: List<ShoppingItem>) {

        for (item in list) {
            if (!item.isActual && !item.isFavorite) {
                useCases.deleteShoppingItem(item)
            }
        }
    }
}