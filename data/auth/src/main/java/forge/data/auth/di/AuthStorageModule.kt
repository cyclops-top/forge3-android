package forge.data.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import forge.data.auth.DefaultTokenStorage
import forge.data.auth.TokenStorage
import forge.network.auth.TokenLoader
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthStorageModule{
    @Singleton
    @Binds
    internal abstract fun tokenStorage(defaultTokenStorage: DefaultTokenStorage): TokenStorage

    @Singleton
    @Binds
    internal abstract fun tokenLoader(tokenStorage: TokenStorage): TokenLoader


}