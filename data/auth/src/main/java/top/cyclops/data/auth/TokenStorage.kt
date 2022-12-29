package top.cyclops.data.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.Lazy
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import top.cyclops.forage.storage.JsonStoreSerializer
import top.cyclops.forge.model.OAuthToken
import top.cyclops.forge.model.Token
import top.cyclops.forge.network.auth.AuthorizationToken
import top.cyclops.forge.network.auth.TokenLoader
import javax.inject.Inject
import kotlin.reflect.typeOf

interface TokenStorage : TokenLoader {
    suspend fun save(oAuthToken: OAuthToken)
    suspend fun getToken(): OAuthToken?
    suspend fun clear()
    override suspend fun get(): String? {
        return getToken()?.accessToken?.raw
    }
}


internal class TokenStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json,
    lazyAuthApi: Lazy<AuthApi>
) : TokenStorage {

    private val Context.tokenStorage: DataStore<OAuthToken?> by dataStore(
        "account-store.db",
        JsonStoreSerializer(json = json, defaultValue = null, type = typeOf<OAuthToken>())
    )

    private val authApi by lazy {
        lazyAuthApi.get()
    }

    override suspend fun save(oAuthToken: OAuthToken) {
        println("====> $oAuthToken")
        context.tokenStorage.updateData { oAuthToken }
    }

    override suspend fun getToken(): OAuthToken? {
        val token = context.tokenStorage.data.first() ?: return null
        if (token.accessToken.isValid) {
            return token
        }
        if (!token.refreshToken.isValid) {
            return null
        }
        val response =
            authApi.refreshToken(token.refreshToken.raw)
        if (!response.isSuccessful) {
            return null
        }
        return response.body()!!.asOAuthToken(json).also {
            save(it)
        }
    }

    override suspend fun clear() {
        context.tokenStorage.updateData { null }
    }

    private val Token.isValid: Boolean
        get() = expirationAt > Clock.System.now()
}