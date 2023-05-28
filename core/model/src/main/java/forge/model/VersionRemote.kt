package forge.model

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Entity(tableName = "version")
data class Version(
    @PrimaryKey
    @SerialName("id")
    val id: Long,
    @SerialName("project")
    val project: Long,
    @SerialName("name")
    val name: String,
    @SerialName("code")
    val code: Int,
    @SerialName("description")
    val description: String,
    @Embedded(prefix = "file_")
    @SerialName("file")
    val file: RemoteFile,
    @SerialName("sign")
    val sign: String,
    @SerialName("tags")
    val tags: List<String>,
    @SerialName("createdBy")
    val createdBy: String,
    @SerialName("createdTime")
    val createdTime: Instant,
)
