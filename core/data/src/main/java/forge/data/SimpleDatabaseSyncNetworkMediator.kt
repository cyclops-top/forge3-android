package forge.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import forge.model.Page

@OptIn(ExperimentalPagingApi::class)
abstract class SimpleDatabaseSyncNetworkMediator<T : Any> : RemoteMediator<Int, T>() {
    private var nextKey: Int? = 0
    abstract suspend fun clearLocal()
    abstract suspend fun insertToLocal(data: List<T>)
    abstract fun pagingSource(): PagingSource<Int, T>
    abstract suspend fun loadFormNetwork(index: Int, size: Int): Result<Page<T>>
    override suspend fun load(loadType: LoadType, state: PagingState<Int, T>): MediatorResult {
        return try {
            val loadSize = state.config.getLoadSize(loadType)
            val index = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {
                    nextKey
                }
            }
            val result = loadFormNetwork(index!!, loadSize)
            if (loadType == LoadType.REFRESH) {
                clearLocal()
            }
            if (result.isSuccess) {
                val data = result.getOrThrow()
                insertToLocal(data.content)
                MediatorResult.Success(
                    endOfPaginationReached = !data.hasNext
                )
            } else {
                MediatorResult.Error(result.exceptionOrNull() ?: Throwable("网络错误"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private fun PagingConfig.getLoadSize(loadType: LoadType): Int {
        return if (loadType == LoadType.REFRESH) {
            initialLoadSize
        } else {
            pageSize
        }
    }
}