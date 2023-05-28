package forge.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import forge.model.Page
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


val <T : Any>Response<Page<T>>.pagingResult: PagingSource.LoadResult<Int, T>
    get() {
        return when {
            this.isSuccessful -> {
                val result = body()!!
                PagingSource.LoadResult.Page(
                    data = result.content,
                    prevKey = null,
                    nextKey = if (result.hasNext) result.number + 1 else null
                )
            }

            else -> {
                PagingSource.LoadResult.Error(Throwable(this.message()))
            }
        }
    }

val <T : Any>Page<T>.nextKey: Int?
    get() = if (hasNext) number + 1 else null


fun <T:Any,U>Response<Page<T>>.map(converter: (T) -> U): Response<Page<U>> {
    return if(isSuccessful){
        Response.success(body()!!.map(converter))
    }else{
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        Response.error(code(),errorBody())
    }
}


internal class SimplePagingSource<T : Any>(val loader: suspend (index: Int, size: Int) -> Response<Page<T>>) :
    PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val index = params.key ?: 0
        val size = params.loadSize
        return try {
            loader(index, size).pagingResult
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}


fun <T : Any> pagingSource(loader: suspend (index: Int, size: Int) -> Response<Page<T>>):
        PagingSource<Int, T> {
    return SimplePagingSource(loader)
}


fun <T : Any> pager(
    pageSize: Int = 20,
    prefetchDistance: Int = pageSize,
    initialLoadSize: Int = pageSize * 3,
    max: Int = Int.MAX_VALUE,
    loader: suspend (index: Int, size: Int) -> Response<Page<T>>,
): Flow<PagingData<T>> {
    return Pager(
        config = PagingConfig(
            pageSize,
            prefetchDistance = prefetchDistance,
            enablePlaceholders = false,
            initialLoadSize = initialLoadSize,
            maxSize = max
        )
    ) {
        pagingSource(loader)
    }.flow
}


@OptIn(ExperimentalPagingApi::class)
fun <T : Any> pager(
    pageSize: Int = 20,
    prefetchDistance: Int = pageSize,
    initialLoadSize: Int = pageSize * 3,
    max: Int = Int.MAX_VALUE,
    remoteMediator: SimpleDatabaseSyncNetworkMediator<T>,
): Flow<PagingData<T>> {
    return Pager(
        config = PagingConfig(
            pageSize,
            prefetchDistance = prefetchDistance,
            enablePlaceholders = false,
            initialLoadSize = initialLoadSize,
            maxSize = max
        ),
        remoteMediator = remoteMediator
    ) {
        remoteMediator.pagingSource()
    }.flow
}
