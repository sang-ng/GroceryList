package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.model.ShoppingItem

@Composable
fun FavoriteListItem(
    shoppingItem: ShoppingItem,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = shoppingItem.title)
        IconButton(onClick = onClicked) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }
}