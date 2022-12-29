package top.cyclops.data.auth

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import top.cyclops.forge.model.Account
import top.cyclops.forge.model.OAuthToken
import top.cyclops.forge.network.auth.Authority
import top.cyclops.forge.network.auth.AuthorizationHeaderKey
import top.cyclops.forge.network.auth.Authorize

internal interface AuthApi {
    @Authorize(Authority.Optional)
    @POST("auth/register")
    suspend fun register(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<Account>

    @Authorize(Authority.Without)
    @POST("auth/sign/in")
    suspend fun signIn(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<JwtOAuthToken>

    @POST("auth/sign/out")
    suspend fun signOut(): Response<Unit>

    @Authorize(Authority.Without)
    @POST("auth/refreshToken")
    suspend fun refreshToken(@Query("refreshToken") refreshToken: String): Response<JwtOAuthToken>

    @GET("account/current")
    suspend fun currentAccount(): Response<Account>
}