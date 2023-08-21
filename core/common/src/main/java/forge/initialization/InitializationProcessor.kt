package forge.initialization

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

internal class InitializationProcessor(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob()),
) {

    fun process(context: Context) {
        val entryPoint = InitializationEntryPoint(context)
        val initializations: Set<Initialization> = entryPoint.initializations()
        val dispatcher = entryPoint.dispatcher()
        validation(initializations)
        initializations.forEach { initialization ->
            scope.launch {
                dispatcher.awaitInitDone(initialization.dependsOns.toList())
                if (initialization.init(context)) {
                    dispatcher.setDone(initialization.name)
                }
            }
        }
    }

    private fun validation(initializations: Set<Initialization>) {
        val initializationMap = HashSet<String>()
        initializations.forEach { i ->
            if (!initializationMap.contains(i.name)) {
                initializationMap.add(i.name)
            } else {
                throw IllegalArgumentException("Initialization(${i.name}) already exists")
            }
        }
        initializations.forEach { i ->
            if (i.dependsOns.isNotEmpty()) {
                if (!initializationMap.containsAll(i.dependsOns)) {
                    val notFount = (i.dependsOns - initializationMap)
                    throw IllegalArgumentException(
                        "Initialization(${i.name}) depends on ${i.dependsOns.joinToString()}" +
                                ", but ${notFount.joinToString()} not found"
                    )
                }
            }
        }
    }
}