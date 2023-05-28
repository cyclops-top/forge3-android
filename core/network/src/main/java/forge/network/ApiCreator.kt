package forge.network

import kotlin.reflect.KClass

interface ApiCreator {
    fun <T : Any> create(clazz: KClass<T>): T

    companion object {
        inline fun <reified T : Any> ApiCreator.create(): T {
            return create(T::class)
        }
    }
}
