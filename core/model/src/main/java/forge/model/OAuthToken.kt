package forge.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @author justin on 2022/12/7
 */
@Serializable
data class OAuthToken(
    @SerialName("access_token")
    val accessToken: Token,
    @SerialName("refresh_token")
    val refreshToken: Token
)
