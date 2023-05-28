package forge.data.auth

import forge.model.Account


interface AuthRepository {

    suspend fun signIn(username: String, password: String): Result<Unit>
    suspend fun currentAccount(): Account?
}

