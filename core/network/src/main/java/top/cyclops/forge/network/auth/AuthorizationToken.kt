package top.cyclops.forge.network.auth

/**
 *
 * @author justin on 2022/12/7
 */

const val BearerPrefix = "Bearer"
const val BasicPrefix = "Basic"

@Suppress("MemberVisibilityCanBePrivate")
sealed class AuthorizationToken private constructor(val prefix: String, val token: String) {
    override fun toString(): String {
        return "$prefix $token"
    }

    class Bearer(token: String) : AuthorizationToken(BearerPrefix, token)
    class Basic(token: String) : AuthorizationToken(BasicPrefix, token)
}

