package forge.feature.auth

import forge.common.TextContent

internal data class SignInState(
    val username: String = "",
    val password: String = "",
){
    val usernameError: TextContent? = usernameError(username)
    val passwordError: TextContent? = passwordError(password)

    internal val isInputValid: Boolean get() = usernameError == null && passwordError == null

    private fun usernameError(username: String): TextContent?{
        val error = if (username.length < 4) {
            "用户名长度必须大于4个字符"
        } else null
        return error?.let { TextContent(it) }
    }


    private fun passwordError(password: String): TextContent?{
        val error = if (password.length < 4) {
            "密码长度必须大于4个字符"
        } else null
        return error?.let { TextContent(it) }
    }

}
