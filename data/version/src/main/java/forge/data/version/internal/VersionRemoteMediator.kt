package forge.data.version.internal

import androidx.paging.PagingSource
import forge.data.SimpleDatabaseSyncNetworkMediator
import forge.data.version.local.VersionDao
import forge.data.version.remote.VersionApi
import forge.model.Page
import forge.model.Version

internal class VersionRemoteMediator(
    private val project: Long,
    private val remote: VersionApi,
    private val local: VersionDao,
) : SimpleDatabaseSyncNetworkMediator<Version>() {
    override suspend fun clearLocal() {
        local.clear(project)
    }

    override fun pagingSource(): PagingSource<Int, Version> {
        return local.pager(project)
    }

    override suspend fun loadFormNetwork(index: Int, size: Int): Result<Page<Version>> {
        return remote.page(project, index, size)
    }

    override suspend fun insertToLocal(data: List<Version>) {
        local.insertAll(data)
    }
}