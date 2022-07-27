package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(text = shoppingItem.title)
        IconButton(
            onClick = onClicked,
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }
}