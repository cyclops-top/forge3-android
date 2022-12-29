package top.cyclops.data.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import top.cyclops.data.auth.AuthApi
import top.cyclops.forge.network.ApiCreator
import top.cyclops.forge.network.ApiCreator.Companion.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthApiModule {

    @Singleton
    @Provides
    internal fun api(apiCreator: ApiCreator): AuthApi = apiCreator.create()
}