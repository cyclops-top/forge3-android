package forge.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import forge.common.TextContent

@Composable
fun TextContent.string(): String {
    return string(LocalContext.current)
}