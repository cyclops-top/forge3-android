package forge.data.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import forge.common.AuthStateProvider
import forge.data.auth.interal.DefaultAuthStateProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthStateProviderModule {

    @Singleton
    @Binds
    internal abstract fun repo(repo: DefaultAuthStateProvider): AuthStateProvider
}