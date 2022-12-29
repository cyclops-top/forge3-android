package top.cyclops.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import top.cyclops.feature.auth.SignInRoute

private const val authRoute = "auth"
const val signInRoute = "$authRoute/sign/in"

fun NavGraphBuilder.authRoute() {
    composable(signInRoute) {
        SignInRoute()
    }
}