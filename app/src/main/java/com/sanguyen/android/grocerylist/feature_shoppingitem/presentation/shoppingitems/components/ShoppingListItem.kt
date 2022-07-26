package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem

@Composable
fun ShoppingListItem(
    shoppingItem: ShoppingItem,
    onChecked: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(checked = shoppingItem.isMarked, onCheckedChange = { onChecked(it) })

        if (shoppingItem.isMarked) {
            Text(
                text = shoppingItem.title,
                style = MaterialTheme.typography.h6.copy(
                    textDecoration = TextDecoration.LineThrough
                )
            )
        } else {
            Text(text = shoppingItem.title, style = MaterialTheme.typography.h6)
        }
    }
}