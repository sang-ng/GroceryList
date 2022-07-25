package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem

@Composable
fun ShoppingItem(
    shoppingItem: ShoppingItem,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Checkbox(checked = false, onCheckedChange = {})
        Text(text = shoppingItem.title)
        IconButton(
            onClick = onDeleteClick,
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete item",
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}