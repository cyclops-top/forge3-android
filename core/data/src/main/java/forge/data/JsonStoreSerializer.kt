package forge.data

import androidx.datastore.core.Serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import kotlinx.serialization.serializer
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class JsonStoreSerializer<T>(
    private val json: Json,
    override val defaultValue: T,
    type: KType
) : Serializer<T> {
    private val serializer =
        json.serializersModule.serializer(type)

    override suspend fun readFrom(input: InputStream): T {
        val bytes = input.readBytes()
        if (bytes.isEmpty()) {
            @Suppress("UNCHECKED_CAST")
            return null as T
        }

        @Suppress("UNCHECKED_CAST")
        return json.decodeFromStream(serializer, ByteArrayInputStream(bytes)) as T
    }

    override suspend fun writeTo(t: T, output: OutputStream) {
        if (t != null) {
            json.encodeToStream(serializer, t, output)
        }
    }

    companion object {
        inline operator fun <reified T : Any> invoke(json: Json, default: T): Serializer<T> {
            return JsonStoreSerializer(json, default, typeOf<T>())
        }
    }
}