package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.util.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites.FavoritesEvents
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.shoppingitems.components.TransparentHintTextField

@Composable
fun GroceryBottomAppBar(
    text: String,
    hint: String,
    onFocusChange: (FocusState) -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean,
    onClick: () -> Unit
) {
    BottomAppBar() {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TransparentHintTextField(
                modifier = modifier.padding(start = 8.dp),
                text = text,
                hint = hint,
                onValueChange = {
                    onValueChange(it)
                },
                onFocusChange = {
                    onFocusChange(it)
                },
                isHintVisible = isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h6.copy(
                    color = Color.White
                )
            )
            IconButton(
                onClick = {
                    onClick()
                }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Save",
                    tint = Color.White
                )
            }
        }
    }
}