package top.cyclops.forge.common

import java.util.Base64


fun ByteArray.toBase64(): String {
    return String(Base64.getEncoder().encode(this))
}

fun String.decodeBase64(): ByteArray {
    return Base64.getDecoder().decode(this)
}
