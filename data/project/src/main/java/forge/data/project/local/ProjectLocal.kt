package forge.data.project.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "product", indices = [Index("isCollected")])
internal data class ProjectLocal(
    @PrimaryKey
    val id: Long ,
    val name: String,
    val description: String,
    val `package`: String,
    val sign: String,
    val icon: String?,
    val private: Boolean,
    val createdBy: Long = 0,
    val isCollected: Boolean?,
    val lastVersion: String?,
    val createdTime: Instant,
    val updateTime: Instant?,
)