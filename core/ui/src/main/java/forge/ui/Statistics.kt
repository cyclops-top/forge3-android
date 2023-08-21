package forge.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import top.cyclops.forge.statistics.StatisticsEvent
import top.cyclops.forge.statistics.view



val LocalEvent = staticCompositionLocalOf<StatisticsEvent> {
    error("LocalEvent not init")
}

@SuppressLint("ComposableNaming")
@Composable
fun statisticsView(name: String, vararg params: Pair<String, Any>) {
    val context = LocalContext.current
    DisposableEffect(name, *params) {
        context.view.start(name, *params)
        onDispose {
            context.view.end(name, *params)
        }
    }
}