package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.InvalidShoppingItemException
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.use_case.ShoppingItemUseCases
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.ShoppingItemsEvent
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.ShoppingItemsViewModel
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
class FavoritesViewModel @Inject constructor(
    private val useCases: ShoppingItemUseCases
) : ViewModel() {
    private val _state = mutableStateOf(FavoritesListState())
    val state = _state

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

    fun onEvent(event: FavoritesEvents) {
        when (event) {
            is FavoritesEvents.EnteredTitle -> {
                _itemTitle.value = itemTitle.value.copy(
                    text = event.value
                )
            }
            is FavoritesEvents.ChangedTitleFocus -> {
                _itemTitle.value = itemTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && itemTitle.value.text.isBlank()
                )
            }
            is FavoritesEvents.SaveItem -> {
                viewModelScope.launch {
                    try {
                        useCases.addShoppingItem(
                            ShoppingItem(
                                title = itemTitle.value.text,
                                isFavorite = true,
                                isMarked = false
                            )
                        )
                        _itemTitle.value = itemTitle.value.copy(
                            text = ""
                        )
                    } catch (e: InvalidShoppingItemException) {

                    }
                }
            }
            is FavoritesEvents.DeleteShoppingItem -> {
                viewModelScope.launch {
                    useCases.deleteShoppingItem(event.shoppingItem)
                    recentlyDeletedItem = event.shoppingItem
                }
            }
            is FavoritesEvents.RestoreShoppingItem -> {
                viewModelScope.launch {
                    useCases.addShoppingItem(recentlyDeletedItem ?: return@launch)
                    recentlyDeletedItem = null
                }
            }
        }
    }

    private fun getShoppingItems() {
        getShoppingItemsJob?.cancel()

        getShoppingItemsJob = useCases.getShoppingItems()
            .onEach { items ->
                _state.value = state.value.copy(
                    favorites = items.filter { it.isFavorite }
                )
            }.launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()

    }
}
