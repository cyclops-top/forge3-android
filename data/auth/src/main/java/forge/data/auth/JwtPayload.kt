package forge.data.auth

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
internal data class JwtPayload(
    @SerialName("id")
    val id: String,
    @SerialName("aud")
    val audience: String,
    @SerialName("iss")
    val issuer: String,
    @SerialName("type")
    val type: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("username")
    val username: String,
    @SerialName("iat")
    val issuedAt: Int,
    @SerialName("exp")
    val expirationAt: Int
)
