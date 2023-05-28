package forge.data.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import forge.data.auth.AuthApi
import forge.network.ApiCreator
import forge.network.ApiCreator.Companion.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthApiModule {

    @Singleton
    @Provides
    internal fun api(apiCreator: ApiCreator): AuthApi = apiCreator.create()
}