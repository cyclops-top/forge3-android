package forge.data.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import forge.data.auth.AuthRepository
import forge.data.auth.interal.DefaultAuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {


    @Singleton
    @Binds
    internal abstract fun repo(repo: DefaultAuthRepository): AuthRepository
}


