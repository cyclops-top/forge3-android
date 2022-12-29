package top.cyclops.forge.network.auth

import top.cyclops.forge.common.toBase64

enum class Authority {
    Required,
    Optional,
    Without
}


const val AuthorizationHeaderKey = "Authorization"

fun buildClientToken(client: String, secret: String): String = "$client:$secret".toByteArray().toBase64()
