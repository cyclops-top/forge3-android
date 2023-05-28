package forge.data.project.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import forge.data.project.remote.ProjectApi
import forge.network.ApiCreator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProjectApiModule{
    @Singleton
    @Provides
    internal fun api(apiCreator: ApiCreator): ProjectApi = apiCreator.create(ProjectApi::class)
}