package top.cyclops.forge.project

import top.cyclops.forge.model.Project
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ProjectRepositoryImpl @Inject constructor(
    private val projectApi: ProjectApi
) : ProjectRepository {
    override suspend fun projects(): List<Project> {
        return projectApi.page(0, 20).body()?.content ?: emptyList()
    }
}