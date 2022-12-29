package top.cyclops.forge.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import top.cyclops.forge.common.TextContent

@Composable
fun TextContent.string(): String {
    return string(LocalContext.current)
}