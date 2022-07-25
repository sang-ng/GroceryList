package com.sanguyen.android.grocerylist.di

import android.app.Application
import androidx.room.Room
import com.sanguyen.android.grocerylist.feature_shoppingitem.data.data_source.ShoppingItemDatabase
import com.sanguyen.android.grocerylist.feature_shoppingitem.data.repository.ShoppingItemRepositoryImpl
import com.sanguyen.android.grocerylist.feature_shoppingitem.domain.repository.ShoppingItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideShoppingItemDatabase(app: Application): ShoppingItemDatabase {
        return Room.databaseBuilder(
            app, ShoppingItemDatabase::class.java,
            ShoppingItemDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideShoppingItemRepository(db: ShoppingItemDatabase): ShoppingItemRepository {
        return ShoppingItemRepositoryImpl(db.shoppingItemDao)
    }

   
}