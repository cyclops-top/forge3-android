package forge.data.version.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import forge.data.version.remote.VersionApi
import forge.network.ApiCreator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VersionModule {
    @Singleton
    @Provides
    internal fun versionApi(apiCreator: ApiCreator): VersionApi = apiCreator.create(VersionApi::class)
}
