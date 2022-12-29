package top.cyclops.data.auth

import kotlinx.serialization.json.Json
import top.cyclops.forge.model.Account
import top.cyclops.forge.network.NetworkException
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
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