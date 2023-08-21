package top.cyclops.forge.statistics

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import forge.common.EntryPointProperty
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.time.Duration.Companion.seconds

private val Context.statisticsEntryPoint by EntryPointProperty<StatisticsEntryPoint>()
val Context.events get() = statisticsEntryPoint.event()
private val Context.session by StatisticsSessionRead()
val Context.view by StatisticsViewProperty()

fun LifecycleOwner.bindStatisticsSession(context: Context) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            context.session.start()
            launch {
                suspendCancellableCoroutine {
                    it.invokeOnCancellation {
                        context.session.end()
                    }
                }
            }
            launch {
                while (true) {
                    delay(55.seconds)
                    context.session.update()
                }
            }
        }
    }
}

fun <T> T.statisticsView(name: String, vararg params: Pair<String, Any>)
        where T : LifecycleOwner, T : Activity {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            view.start(name, *params)
            launch {
                suspendCancellableCoroutine { co ->
                    co.invokeOnCancellation {
                        view.end(name, *params)
                    }
                }
            }
        }
    }
}


fun <T> T.statisticsView(name: String, vararg params: Pair<String, Any>)
        where T : LifecycleOwner, T : Fragment {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            requireContext().view.start(name, *params)
            launch {
                suspendCancellableCoroutine { co ->
                    co.invokeOnCancellation {
                        requireContext().view.end(name, *params)
                    }
                }
            }
        }
    }
}


private class StatisticsSessionRead : ReadOnlyProperty<Context, StatisticsSession> {
    private lateinit var session: StatisticsSession
    override fun getValue(thisRef: Context, property: KProperty<*>): StatisticsSession {
        if (this::session.isInitialized) {
            return session
        }
        session = thisRef.statisticsEntryPoint.session()
        return session
    }
}

private class StatisticsViewProperty : ReadOnlyProperty<Context, StatisticsView> {
    private lateinit var view: StatisticsView
    override fun getValue(thisRef: Context, property: KProperty<*>): StatisticsView {
        if (this::view.isInitialized) {
            return view
        }
        view = thisRef.statisticsEntryPoint.view()
        return view
    }
}