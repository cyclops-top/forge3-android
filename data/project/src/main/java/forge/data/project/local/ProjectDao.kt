package forge.data.project.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(collection: Collection<ProjectLocal>)

    @Query("select * from product order by isCollected desc,id desc")
    fun pager(): PagingSource<Int, ProjectLocal>

    @Query("delete from product")
    suspend fun clear()

    @Query("select * from product where id = :id")
    fun detailStream(id: Long): Flow<ProjectLocal?>

    @Query("update product set isCollected = 1 where id = :id")
    suspend fun collect(id: Long)

    @Query("update product set isCollected = 0 where id = :id")
    suspend fun cancelCollect(id: Long)

}