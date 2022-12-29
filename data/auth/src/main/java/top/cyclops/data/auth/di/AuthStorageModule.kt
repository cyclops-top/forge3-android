package top.cyclops.data.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import top.cyclops.data.auth.TokenStorage
import top.cyclops.data.auth.TokenStorageImpl
import top.cyclops.forge.network.auth.TokenLoader
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthStorageModule{
    @Singleton
    @Binds
    internal abstract fun tokenStorage(tokenStorageImpl: TokenStorageImpl): TokenStorage

    @Singleton
    @Binds
    internal abstract fun tokenLoader(tokenStorage: TokenStorage): TokenLoader


}