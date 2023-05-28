package forge.feature.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import forge.ui.onFirstLoseFocus
import forge.ui.rememberSnackbarHostState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


@Composable
internal fun SignInRoute(
    onBackClick: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val result by viewModel.signInResult.collectAsStateWithLifecycle(initialValue = null)
    if(result?.isSuccess == true){
        onBackClick()
    }
    SignInScreen(
        state,
        viewModel::updateUsername,
        viewModel::updatePassword,
        viewModel::signIn,
    )
}

@Composable
private fun SignInScreen(
    state: SignInState,
    updateUsername: (String) -> Unit,
    updatePassword: (String) -> Unit,
    signIn: () -> Unit,
) {
    val snackbarHostState = rememberSnackbarHostState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            MediumTopAppBar(title = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.sign_in), modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
            })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var passwordVisibility by remember {
                mutableStateOf(false)
            }
            var usernameLostFocusOnce by remember {
                mutableStateOf(false)
            }
            var passwordLostFocusOnce by remember {
                mutableStateOf(false)
            }
            OutlinedTextField(
                modifier = Modifier.onFirstLoseFocus {
                    usernameLostFocusOnce = true
                },
                value = state.username,
                onValueChange = updateUsername,
                isError = usernameLostFocusOnce && state.usernameError != null,
                label = {
                    Text(text = stringResource(R.string.username))
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "")
                },
                trailingIcon = {
                    if (state.username.isNotBlank()) {
                        IconButton(onClick = { updateUsername("") }) {
                            Icon(imageVector = Icons.Filled.Cancel, contentDescription = "")
                        }
                    }
                },
                singleLine = true
            )

            OutlinedTextField(
                modifier = Modifier.onFirstLoseFocus {
                    passwordLostFocusOnce = true
                },
                value = state.password,
                onValueChange = updatePassword,
                isError = passwordLostFocusOnce && state.passwordError != null,
                label = {
                    Text(text = stringResource(R.string.password))
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "")
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        if (passwordVisibility) {
                            Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = "")
                        } else {
                            Icon(imageVector = Icons.Filled.Visibility, contentDescription = "")
                        }
                    }
                },
                singleLine = true
            )
            Button(enabled = state.isInputValid,onClick = {
                val error =
                    state.usernameError?.string(context) ?: state.passwordError?.string(context)
                if (!error.isNullOrEmpty()) {
                    scope.launch {
                        snackbarHostState.showSnackbar(error)
                    }
                } else {
                    signIn()
                }
            }) {
                Text(text = stringResource(id = R.string.sign_in).uppercase())
            }
            var loading by remember {
                mutableStateOf(false)
            }
            LoadingButton(loading, onClick = {
                loading = true
                scope.launch {
                    delay(3.seconds)
                    loading = false
                }
            }) {
                Text(text = "Loading test button", overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
            }
        }
    }

}


@Composable
fun LoadingButton(
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !isLoading,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
    ) {
        AnimatedContent(
            targetState = isLoading,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform(clip = true)
            },
            contentAlignment = Alignment.Center,
        ) { loading ->
            if (loading) {
                CircularProgressIndicator(
                    color = LocalContentColor.current,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                content()
            }
        }
    }

}