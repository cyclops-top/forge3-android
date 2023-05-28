package forge.data.auth.interal

import forge.data.auth.AuthApi
import forge.data.auth.AuthRepository
import forge.data.auth.TokenStorage
import forge.data.auth.asOAuthToken
import forge.model.Account
import forge.network.NetworkException
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class DefaultAuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage,
    private val json: Json
) : AuthRepository {
    override suspend fun signIn(username: String, password: String): Result<Unit> {
        val signInResponse = authApi.signIn(username, password)
        if (!signInResponse.isSuccessful) {
            return Result.failure(NetworkException(signInResponse.message()))
        }
        val token = signInResponse.body()!!
        tokenStorage.save(token.asOAuthToken(json))
        return Result.success(Unit)
    }

    override suspend fun currentAccount(): Account? {
        return authApi.currentAccount().body()
    }
}