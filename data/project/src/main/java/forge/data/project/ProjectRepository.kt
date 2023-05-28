package forge.data.project

import androidx.paging.PagingData
import forge.model.Project
import kotlinx.coroutines.flow.Flow


interface ProjectRepository {
    fun projects(): Flow<PagingData<Project>>
    fun detailStream(id: Long): Flow<Project>
    suspend fun collect(id: Long): Boolean
    suspend fun cancelCollect(id: Long): Boolean
}

