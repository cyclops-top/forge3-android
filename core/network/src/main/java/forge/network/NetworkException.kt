package forge.network

import forge.common.TextContent

class NetworkException(
    message: TextContent? = null,
    cause: Throwable? = null,
) : Throwable(message?.toString(), cause) {
    constructor(message: String, cause: Throwable? = null) : this(TextContent(message), cause)
}