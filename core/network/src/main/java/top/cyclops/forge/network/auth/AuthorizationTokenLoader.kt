package top.cyclops.forge.network.auth

import top.cyclops.forge.network.NetworkException

sealed interface AuthorizationTokenLoader {
    suspend fun get(authority: Authority): AuthorizationToken

    companion object {
        operator fun invoke(
            tokenLoader: TokenLoader,
            clientToken: String
        ): AuthorizationTokenLoader {
            return AuthorizationTokenLoaderImpl(tokenLoader, clientToken)
        }
    }

    private class AuthorizationTokenLoaderImpl(
        private val tokenLoader: TokenLoader,
        clientToken: String
    ) :
        AuthorizationTokenLoader {

        private suspend fun getBearerToken(): AuthorizationToken? {
            return tokenLoader.get()?.let { AuthorizationToken.Bearer(it) }
        }

        private val basicToken = AuthorizationToken.Basic(clientToken)

        override suspend fun get(authority: Authority): AuthorizationToken {
            return when (authority) {
                Authority.Required -> getBearerToken() ?: throw NetworkException("Unauthorized")
                Authority.Optional -> getBearerToken() ?: basicToken
                Authority.Without -> basicToken
            }
        }
    }
}
