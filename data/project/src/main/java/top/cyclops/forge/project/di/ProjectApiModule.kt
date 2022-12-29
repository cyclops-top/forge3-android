package top.cyclops.forge.project.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import top.cyclops.forge.network.ApiCreator
import top.cyclops.forge.project.ProjectApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProjectApiModule{
    @Singleton
    @Provides
    internal fun api(apiCreator: ApiCreator): ProjectApi = apiCreator.create(ProjectApi::class)
}