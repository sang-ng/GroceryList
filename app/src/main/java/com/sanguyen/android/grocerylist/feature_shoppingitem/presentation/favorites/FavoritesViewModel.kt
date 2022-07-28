package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites

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
class FavoritesViewModel @Inject constructor(
    private val useCases: ShoppingItemUseCases
) : ViewModel() {
    private val _state = mutableStateOf(FavoritesListState())
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
                                isMarked = false,
                                isActual = false
                            )
                        )
                        _itemTitle.value = itemTitle.value.copy(
                            text = ""
                        )
                    } catch (e: InvalidShoppingItemException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
            is FavoritesEvents.RemoveFromFavorites -> {
                viewModelScope.launch {

                    event.shoppingItem.isFavorite = false
                    useCases.updateShoppingItem(event.shoppingItem)

                    recentlyRemovedItem = event.shoppingItem

                   refreshList(event.shoppingItem, "isFavorite")
                }
            }
            is FavoritesEvents.RestoreFavorite -> {
                viewModelScope.launch {

                    recentlyRemovedItem?.isFavorite = true
                    recentlyRemovedItem?.let { useCases.addShoppingItem(it) }
                    recentlyRemovedItem = null
                }
            }
            is FavoritesEvents.AddToActualList -> {
                viewModelScope.launch {
                    event.shoppingItem.isActual = true
                    useCases.updateShoppingItem(event.shoppingItem)

                    refreshList(event.shoppingItem, "isActual")
                }
            }
        }
    }

    private fun refreshList(shoppingItem: ShoppingItem, toMap: String) {
        val updatedList = emptyList<ShoppingItem>()
        if (toMap == "isFavorite") {
            _state.value.favorites =
                _state.value.favorites.map { if (it.id == shoppingItem.id) it.copy(isFavorite = !it.isFavorite) else it }
        }
        if (toMap == "isActual") {
            _state.value.favorites =
                _state.value.favorites.map { if (it.id == shoppingItem.id) it.copy(isActual = !it.isActual) else it }
        }
        _state.value.favorites = updatedList
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


}
