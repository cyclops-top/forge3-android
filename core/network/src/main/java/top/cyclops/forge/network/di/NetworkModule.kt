package top.cyclops.forge.network.di

import android.content.Context
import com.google.net.cronet.okhttptransport.CronetInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.chromium.net.CronetEngine
import retrofit2.Retrofit
import top.cyclops.forge.common.ForgeConfig
import top.cyclops.forge.network.ApiCreator
import top.cyclops.forge.network.NetworkBigDecimalConverter
import top.cyclops.forge.network.NetworkStringConverter
import top.cyclops.forge.network.asConverterFactory
import top.cyclops.forge.network.auth.AuthorizationInterceptor
import top.cyclops.forge.network.auth.TokenLoader
import javax.inject.Singleton
import kotlin.reflect.KClass


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {


    @Provides
    @Singleton
    internal fun json(module: SerializersModule): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            explicitNulls = false
            serializersModule = module
        }
    }

    @Provides
    @Singleton
    internal fun cronet(@ApplicationContext context: Context): CronetEngine {
        return CronetEngine.Builder(context).build()
    }

    @Provides
    @Singleton
    internal fun client(
        engine: CronetEngine,
        config: ForgeConfig,
        tokenLoader: TokenLoader
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(tokenLoader, config.client, config.secret))
            .addInterceptor(CronetInterceptor.newBuilder(engine).build())
            .build()
    }

    @Provides
    @Singleton
    internal fun retrofit(config: ForgeConfig, client: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(config.baseUrl)
            .addConverterFactory(NetworkBigDecimalConverter.factory())
            .addConverterFactory(NetworkStringConverter.factory())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }


    @Provides
    @Singleton
    internal fun apiCreator(retrofit: Retrofit): ApiCreator {
        return object : ApiCreator {
            override fun <T : Any> create(clazz: KClass<T>): T {
                return retrofit.create(clazz.java)
            }
        }
    }
}