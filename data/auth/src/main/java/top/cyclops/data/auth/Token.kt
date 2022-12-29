package top.cyclops.data.auth

import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import top.cyclops.forge.common.decodeBase64
import top.cyclops.forge.model.OAuthToken
import top.cyclops.forge.model.Token


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