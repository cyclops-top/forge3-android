package forge.network.auth

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation


internal class AuthorizationInterceptor(
    tokenLoader: TokenLoader,
    client: String,
    secret: String,
    private val defaultAuthority: Authority = Authority.Required
) : Interceptor {
    private val authorizationTokenLoader =
        AuthorizationTokenLoader(tokenLoader, buildClientToken(client, secret))

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if(request.headers[AuthorizationHeaderKey] != null){
            return chain.proceed(request)
        }
        val token = runBlocking {
            authorizationTokenLoader.get(request.authority)
        }
        return chain.proceed(
            request.newBuilder()
                .addHeader(AuthorizationHeaderKey, token.toString())
                .build()
        )

    }


    private val Request.authority: Authority
        get() = this.tag(Invocation::class.java)?.method()
            ?.getAnnotation(Authorize::class.java)?.authority ?: defaultAuthority
}

