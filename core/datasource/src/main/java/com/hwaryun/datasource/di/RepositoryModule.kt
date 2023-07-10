package com.hwaryun.datasource.di

import com.hwaryun.datasource.repository.auth.AuthRepository
import com.hwaryun.datasource.repository.auth.AuthRepositoryImpl
import com.hwaryun.datasource.repository.cart.CartRepository
import com.hwaryun.datasource.repository.cart.CartRepositoryImpl
import com.hwaryun.datasource.repository.food.FoodRepository
import com.hwaryun.datasource.repository.food.FoodRepositoryImpl
import com.hwaryun.datasource.repository.profile.ProfileRepository
import com.hwaryun.datasource.repository.profile.ProfileRepositoryImpl
import com.hwaryun.datasource.repository.transaction.TransactionRepository
import com.hwaryun.datasource.repository.transaction.TransactionRepositoryImpl
import com.hwaryun.datasource.repository.wishlist.WishlistRepository
import com.hwaryun.datasource.repository.wishlist.WishlistRepositoryImpl
import com.hwaryun.datasource.util.ConnectivityManagerNetworkMonitor
import com.hwaryun.datasource.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun bindFoodRepository(foodRepositoryImpl: FoodRepositoryImpl): FoodRepository

    @Singleton
    @Binds
    abstract fun bindTransactionRepository(transactionRepositoryImpl: TransactionRepositoryImpl): TransactionRepository

    @Singleton
    @Binds
    abstract fun bindCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository

    @Singleton
    @Binds
    abstract fun bindWishlistRepository(wishlistRepositoryImpl: WishlistRepositoryImpl): WishlistRepository

    @Singleton
    @Binds
    abstract fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Singleton
    @Binds
    abstract fun bindNetworkMonitor(networkMonitor: ConnectivityManagerNetworkMonitor): NetworkMonitor
}