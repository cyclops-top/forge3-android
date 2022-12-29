package top.cyclops.forge.model

import androidx.annotation.Keep
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class Token(
    @SerialName("user_id")
    val userId: Long,
    @SerialName("username")
    val username: String,
    @SerialName("iat")
    val issuedAt: Instant,
    @SerialName("exp")
    val expirationAt: Instant,
    @SerialName("raw")
    val raw: String,
)