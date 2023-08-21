package forge.initialization

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface InitializationEntryPoint {
    fun initializations(): Set<@JvmSuppressWildcards Initialization>
    fun dispatcher(): InitializationDispatcher

    companion object {
        operator fun invoke(context: Context): InitializationEntryPoint {
            return EntryPointAccessors.fromApplication(context)
        }
    }
}