package top.cyclops.forge.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 *
 * @author justin on 2022/12/3
 */

@Serializable
data class Project(
    val id: Long = 0,
    val name: String,
    val description: String,
    val `package`: String,
    val sign: String,
    val icon: String?,
    val private: Boolean,
    val createdBy: Long = 0,
    val createdTime: Instant,
    val updateTime: Instant?
)


