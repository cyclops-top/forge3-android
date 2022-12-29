package top.cyclops.forge.network

import top.cyclops.forge.common.TextContent

class NetworkException(
    message: TextContent? = null,
    cause: Throwable? = null,
) : RuntimeException(message?.toString(), cause) {
    constructor(message: String, cause: Throwable? = null) : this(TextContent(message), cause)
}