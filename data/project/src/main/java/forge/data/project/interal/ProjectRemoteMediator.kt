package forge.data.project.interal

import androidx.paging.PagingSource
import forge.data.SimpleDatabaseSyncNetworkMediator
import forge.data.project.local.ProjectDao
import forge.data.project.local.ProjectLocal
import forge.data.project.remote.ProjectApi
import forge.model.Page

internal class ProjectRemoteMediator(
    private val remote: ProjectApi,
    private val local: ProjectDao,
) : SimpleDatabaseSyncNetworkMediator<ProjectLocal>() {
    override suspend fun clearLocal() {
        local.clear()
    }

    override fun pagingSource(): PagingSource<Int, ProjectLocal> {
        return local.pager()
    }

    override suspend fun loadFormNetwork(index: Int, size: Int): Result<Page<ProjectLocal>> {
        return remote.page(index,size).map { it.map { item -> item.toLocal() } }
    }

    override suspend fun insertToLocal(data: List<ProjectLocal>) {
       local.insertAll(data)
    }
}