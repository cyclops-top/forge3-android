package forge.network


import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialFormat
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.serializer
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


private sealed class Serializer {
    abstract fun <T> fromResponseBody(loader: DeserializationStrategy<T>, body: ResponseBody): T?
    abstract fun <T> toRequestBody(
        contentType: MediaType,
        saver: SerializationStrategy<T>,
        value: T,
    ): RequestBody

    protected abstract val format: SerialFormat

    @ExperimentalSerializationApi // serializer(Type) is not stable.
    fun serializer(type: Type): KSerializer<Any> = format.serializersModule.serializer(type)

    class FromString(override val format: StringFormat) : Serializer() {
        override fun <T> fromResponseBody(
            loader: DeserializationStrategy<T>,
            body: ResponseBody,
        ): T? {
            val string = body.string()
            if (string.isEmpty()) {
                return null
            }
            return format.decodeFromString(loader, string)
        }

        override fun <T> toRequestBody(
            contentType: MediaType,
            saver: SerializationStrategy<T>,
            value: T,
        ): RequestBody {
            val string = format.encodeToString(saver, value)
            return string.toRequestBody(contentType)
        }
    }

    class FromBytes(override val format: BinaryFormat) : Serializer() {
        override fun <T> fromResponseBody(
            loader: DeserializationStrategy<T>,
            body: ResponseBody,
        ): T {
            val bytes = body.bytes()
            return format.decodeFromByteArray(loader, bytes)
        }

        override fun <T> toRequestBody(
            contentType: MediaType,
            saver: SerializationStrategy<T>,
            value: T,
        ): RequestBody {
            val bytes = format.encodeToByteArray(saver, value)
            return bytes.toRequestBody(contentType, 0, bytes.size)
        }
    }
}

private class DeserializationStrategyConverter<T>(
    private val loader: DeserializationStrategy<T>,
    private val serializer: Serializer,
) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody) = serializer.fromResponseBody(loader, value)
}

private class SerializationStrategyConverter<T>(
    private val contentType: MediaType,
    private val saver: SerializationStrategy<T>,
    private val serializer: Serializer,
) : Converter<T, RequestBody> {
    override fun convert(value: T) = serializer.toRequestBody(contentType, saver, value)
}


private class DeserializationStrategyResultConverter<T>(
    private val loader: DeserializationStrategy<T>,
    private val serializer: Serializer,
) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T? {
        return serializer.fromResponseBody(loader, value)
    }
}

@ExperimentalSerializationApi
private class Factory(
    private val contentType: MediaType,
    private val serializer: Serializer,
) : Converter.Factory() {
    @Suppress("RedundantNullableReturnType") // Retaining interface contract.
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, *>? {
        val loader = serializer.serializer(type)
        return DeserializationStrategyResultConverter(loader, serializer)
    }

    @Suppress("RedundantNullableReturnType") // Retaining interface contract.
    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): Converter<*, RequestBody>? {
        val saver = serializer.serializer(type)
        return SerializationStrategyConverter(contentType, saver, serializer)
    }
}

/**
 * Return a [Converter.Factory] which uses Kotlin serialization for string-based payloads.
 *
 * Because Kotlin serialization is so flexible in the types it supports, this converter assumes
 * that it can handle all types. If you are mixing this with something else, you must add this
 * instance last to allow the other converters a chance to see their types.
 */
@Suppress("unused")
@ExperimentalSerializationApi
@JvmName("create")
fun StringFormat.asConverterFactory(contentType: MediaType): Converter.Factory {
    return Factory(contentType, Serializer.FromString(this))
}

/**
 * Return a [Converter.Factory] which uses Kotlin serialization for byte-based payloads.
 *
 * Because Kotlin serialization is so flexible in the types it supports, this converter assumes
 * that it can handle all types. If you are mixing this with something else, you must add this
 * instance last to allow the other converters a chance to see their types.
 */
@Suppress("unused")
@ExperimentalSerializationApi
@JvmName("create")
fun BinaryFormat.asConverterFactory(contentType: MediaType): Converter.Factory {
    return Factory(contentType, Serializer.FromBytes(this))
}