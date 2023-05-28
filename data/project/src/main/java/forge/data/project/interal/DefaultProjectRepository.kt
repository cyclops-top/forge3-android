package forge.data.project.interal

import androidx.paging.PagingData
import androidx.paging.map
import forge.common.AuthEventHandler
import forge.common.AuthStateProvider
import forge.data.pager
import forge.data.project.ProjectRepository
import forge.data.project.local.ProjectDao
import forge.data.project.remote.ProjectApi
import forge.model.Project
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultProjectRepository @Inject constructor(
    private val projectApi: ProjectApi,
    private val projectDao: ProjectDao,
    private val authStateProvider: AuthStateProvider,
    private val authEventHandler: AuthEventHandler,
) : ProjectRepository {
    override fun projects(): Flow<PagingData<Project>> {
        return authStateProvider.authStateStream.map { it.isAuthorized }.distinctUntilChanged()
            .filter { it }.flatMapLatest {
                pager(
                    remoteMediator = ProjectRemoteMediator(
                        projectApi,
                        projectDao,
                    )
                ).map { pd -> pd.map { it.toProject() } }
            }
    }

    override fun detailStream(id: Long): Flow<Project> {
        return channelFlow {
            launch {
                val project = projectApi.detail(id).body()?.toLocal()
                if (project != null) {
                    projectDao.insertAll(listOf(project))
                }
            }
            launch {
                projectDao.detailStream(id).filterNotNull().collectLatest {
                    send(it.toProject())
                }
            }
        }
    }

    override suspend fun collect(id: Long): Boolean {
        if (!authStateProvider.currentAuthState().isAuthorized) {
            authEventHandler.notifyUnauthorized()
            return false
        }
        return projectApi.collect(id)
            .let { it.isSuccessful && it.body() == true }
            .also {
                if (it) {
                    projectDao.collect(id)
                }
            }
    }

    override suspend fun cancelCollect(id: Long): Boolean {
        if (!authStateProvider.currentAuthState().isAuthorized) {
            authEventHandler.notifyUnauthorized()
            return false
        }
        return projectApi.cancelCollect(id)
            .let { it.isSuccessful && it.body() == true }
            .also {
                if (it) {
                    projectDao.cancelCollect(id)
                }
            }
    }
}