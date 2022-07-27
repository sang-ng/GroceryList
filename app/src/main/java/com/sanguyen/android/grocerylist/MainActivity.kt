package com.sanguyen.android.grocerylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.NavGraph
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.NavGraphs
import com.sanguyen.android.grocerylist.feature_shoppingitem.presentation.favorites.FavoritesScreen
import com.sanguyen.android.grocerylist.ui.theme.GroceryListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroceryListTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
