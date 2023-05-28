package forge.data.project.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import forge.data.project.ProjectRepository
import forge.data.project.interal.DefaultProjectRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProjectRepositoryModule {
    @Singleton
    @Binds
    internal abstract fun repo(repo: DefaultProjectRepository): ProjectRepository
}

