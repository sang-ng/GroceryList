package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Insert
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.InvalidShoppingItemException
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.use_case.ShoppingItemUseCases
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.components.ItemTextFieldState
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
                                isFavorite = false
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
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

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
        object DeleteNote : UiEvent()
    }
}