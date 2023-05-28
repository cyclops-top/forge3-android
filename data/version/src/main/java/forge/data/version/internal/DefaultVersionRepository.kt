package forge.data.version.internal

import androidx.paging.PagingData
import forge.data.pager
import forge.data.version.VersionRepository
import forge.data.version.local.VersionDao
import forge.data.version.remote.VersionApi
import forge.model.Version
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultVersionRepository @Inject constructor(
    private val versionApi: VersionApi,
    private val versionDao: VersionDao,
) : VersionRepository {
    override fun versions(project: Long): Flow<PagingData<Version>> {
        return pager(remoteMediator = VersionRemoteMediator(project, versionApi, versionDao))
    }
}
