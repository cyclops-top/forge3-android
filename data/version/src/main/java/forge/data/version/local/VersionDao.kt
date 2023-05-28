package forge.data.version.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import forge.model.Version

@Dao
internal interface VersionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(collection: Collection<Version>)

    @Query("select * from version where project = :project order by id desc")
    fun pager(project: Long): PagingSource<Int, Version>

    @Query("delete from version where project = :project ")
    suspend fun clear(project: Long)

}