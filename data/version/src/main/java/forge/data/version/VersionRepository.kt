package forge.data.version

import androidx.paging.PagingData
import forge.model.Version
import kotlinx.coroutines.flow.Flow


interface VersionRepository {
    fun versions(project: Long): Flow<PagingData<Version>>
}

