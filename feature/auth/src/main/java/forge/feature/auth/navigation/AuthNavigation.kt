package forge.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import forge.feature.auth.SignInRoute

private const val authRoute = "auth"
const val signInRoute = "$authRoute/sign/in"

fun NavGraphBuilder.authRoute(onBackClick: () -> Unit,) {
    composable(signInRoute) {
        SignInRoute(onBackClick)
    }
}