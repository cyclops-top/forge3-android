package top.cyclops.forge.network

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.http.RealResponseBody
import okio.buffer
import okio.source

val jsonContentType = "application/json".toMediaType()
internal fun Request.createResponse(code: Int, message: String, data:String): Response {
    val realBody = RealResponseBody(
        jsonContentType.toString(),
        data.length.toLong(), data.byteInputStream().source().buffer()
    )
    return Response.Builder()
        .request(this)
        .code(code)
        .protocol(Protocol.HTTP_1_1)
        .message(message)
        .body(realBody)
        .build()
}

fun <T> retrofit2.Response<T>.parseBody(): T? {
    return if (isSuccessful) {
        return body()
    } else {
        null
    }
}