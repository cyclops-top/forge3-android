package top.cyclops.data.auth

import top.cyclops.forge.model.Account


interface AuthRepository {

    suspend fun signIn(username: String, password: String): Result<Unit>
    suspend fun currentAccount(): Account?
}

