package forge.common

import android.content.Context
import dagger.hilt.android.EntryPointAccessors
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class EntryPointProperty<T : Any>(private val entryPointClass: Class<T>) :
    ReadOnlyProperty<Context, T> {
    private lateinit var entity: T
    override fun getValue(thisRef: Context, property: KProperty<*>): T {
        if (this::entity.isInitialized) {
            return this.entity
        }
        this.entity = EntryPointAccessors.fromApplication(thisRef, entryPointClass)
        return entity
    }

    companion object {
        inline operator fun <reified T : Any> invoke(): ReadOnlyProperty<Context, T> {
            return EntryPointProperty(T::class.java)
        }
    }
}