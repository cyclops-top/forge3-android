package forge.initialization

import android.content.Context

interface Initialization {
    val name: String
    val dependsOns: Set<String> get() = emptySet()
    suspend fun init(context: Context): Boolean
}


