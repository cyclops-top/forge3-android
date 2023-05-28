
package forge.data.version.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import forge.data.version.VersionRepository
import forge.data.version.internal.DefaultVersionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class VersionRepositoryModule {

    @Singleton
    @Binds
    internal abstract fun repo(repo: DefaultVersionRepository): VersionRepository
}
