package forge.initialization

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

interface InitializationDispatcher {
    suspend fun awaitInitDone(depends: List<String>)
    fun initDoneStream(depends: List<String>): Flow<Set<String>>
    fun setDone(name: String)
}



@Singleton
internal class DefaultInitializationDispatcher @Inject constructor() : InitializationDispatcher {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val doneEvent = MutableSharedFlow<String>()
    private val initializationsDoneStream =
        doneEvent.runningFold(emptySet<String>()) { set, name ->
            set.toMutableSet()
                .apply {
                    add(name)
                }
        }.stateIn(scope, SharingStarted.Eagerly, emptySet())

    override suspend fun awaitInitDone(depends: List<String>) {
        if (depends.isNotEmpty()) {
            initializationsDoneStream.first { it.containsAll(depends) }
        }
    }

    override fun initDoneStream(depends: List<String>): Flow<Set<String>> {
        return initializationsDoneStream.map { depends.intersect(it) }.distinctUntilChanged()
    }

    override fun setDone(name: String) {
        scope.launch {
            doneEvent.emit(name)
        }
    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class InitializationDispatcherModule {
    @Singleton
    @Binds
    internal abstract fun dispatcher(repo: DefaultInitializationDispatcher): InitializationDispatcher
}
