package top.cyclops.data.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import top.cyclops.data.auth.AuthRepository
import top.cyclops.data.auth.AuthRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {


    @Singleton
    @Binds
    internal abstract fun repo(repo: AuthRepositoryImpl): AuthRepository
}


