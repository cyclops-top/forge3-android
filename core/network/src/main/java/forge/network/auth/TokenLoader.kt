package forge.network.auth

interface TokenLoader {
    suspend fun get(): String?
}