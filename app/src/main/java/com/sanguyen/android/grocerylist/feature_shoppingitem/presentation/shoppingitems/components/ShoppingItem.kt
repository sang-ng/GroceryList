package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem

@Composable
fun ShoppingItem(
    shoppingItem: ShoppingItem,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(checked = false, onCheckedChange = {})
        Text(text = shoppingItem.title)
    }
}