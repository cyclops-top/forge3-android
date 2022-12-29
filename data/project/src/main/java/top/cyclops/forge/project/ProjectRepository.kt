package top.cyclops.forge.project

import top.cyclops.forge.model.Project


interface ProjectRepository {
    suspend fun projects(): List<Project>
}

