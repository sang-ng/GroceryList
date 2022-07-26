package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem

@Composable
fun ShoppingListItem(
    shoppingItem: ShoppingItem,
    onChecked: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(checked = shoppingItem.isMarked, onCheckedChange = { onChecked(it) })
        Text(text = shoppingItem.title)
    }
}