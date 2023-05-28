package forge.data.project.remote

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ProjectRemote(
    val id: Long = 0,
    val name: String,
    val description: String,
    val `package`: String,
    val sign: String,
    val icon: String?,
    val private: Boolean,
    val isCollected: Boolean?,
    val lastVersion: String?,
    val createdBy: Long = 0,
    val createdTime: Instant,
    val updateTime: Instant?,
)