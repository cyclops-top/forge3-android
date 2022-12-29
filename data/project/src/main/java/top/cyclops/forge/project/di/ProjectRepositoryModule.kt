package top.cyclops.forge.project.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import top.cyclops.forge.project.ProjectRepository
import top.cyclops.forge.project.ProjectRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProjectRepositoryModule {
    @Singleton
    @Binds
    internal abstract fun repo(repo: ProjectRepositoryImpl): ProjectRepository
}

