package top.cyclops.forge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import dagger.hilt.android.AndroidEntryPoint
import forge.ui.LocalEvent
import kotlinx.coroutines.launch
import top.cyclops.forge.statistics.bindStatisticsSession
import top.cyclops.forge.statistics.events
import top.cyclops.forge.ui.ForgeApp


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    init {
        lifecycleScope.launch {
            withCreated {
                bindStatisticsSession(this@MainActivity)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalEvent.providesDefault(events)) {
                ForgeApp()
            }
        }
    }
}
