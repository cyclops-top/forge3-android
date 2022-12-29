package top.cyclops.forge.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Account(
    val id: Long = 0,
    val username: String,
    val user: Long,
    val enable: Boolean = true,
    var createdTime: Instant,

    var updateTime: Instant?
)