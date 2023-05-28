package forge.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val id: Long = 0,
    val username: String,
    val user: Long,
    val enable: Boolean = true,
    var createdTime: Instant,

    var updateTime: Instant?
)