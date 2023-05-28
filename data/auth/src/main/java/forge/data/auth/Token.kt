package forge.data.auth

import forge.common.decodeBase64
import forge.model.OAuthToken
import forge.model.Token
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream


private fun parseToken(json: Json, token: String): Token {
    val (_, payloadBase64) = token.split(".")
    val payload: JwtPayload =
        payloadBase64.decodeBase64().inputStream().use(json::decodeFromStream)
    return Token(
        userId = payload.userId.toLongOrNull() ?: 0,
        username = payload.username,
        issuedAt = Instant.fromEpochMilliseconds(payload.issuedAt * 1000L),
        expirationAt = Instant.fromEpochMilliseconds(payload.expirationAt * 1000L),
        raw = token
    )
}

internal fun JwtOAuthToken.asOAuthToken(json: Json): OAuthToken {
    return OAuthToken(
        accessToken = parseToken(json, accessToken),
        refreshToken = parseToken(json, refreshToken)
    )
}