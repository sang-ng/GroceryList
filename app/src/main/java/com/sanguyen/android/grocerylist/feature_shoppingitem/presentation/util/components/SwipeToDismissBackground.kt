package com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.util.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites.FavoritesEvents
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites.components.FavoriteListItem
import com.sanguyen.android.grocerylist.ui.theme.LightRed

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToDismissBackground(dismissState : DismissState) {
    val color by animateColorAsState(
        targetValue = if (dismissState.targetValue == DismissValue.Default) LightRed else Color.Red
    )

    val scale by animateFloatAsState(
        targetValue =
        if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "Icon",
            modifier = Modifier
                .scale(scale)
                .padding(16.dp),
            tint = Color.White
        )
    }
}