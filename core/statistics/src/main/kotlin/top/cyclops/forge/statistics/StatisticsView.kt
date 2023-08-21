package top.cyclops.forge.statistics

import dagger.BindsOptionalOf
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

interface StatisticsView {
    fun start(name: String, vararg params: Pair<String, Any>)
    fun end(name: String, vararg params: Pair<String, Any>)
}

internal class StatisticsViewDelegate(private val view: StatisticsView?) : StatisticsView {
    override fun start(name: String, vararg params: Pair<String, Any>) {
        view?.start(name, *params)
    }

    override fun end(name: String, vararg params: Pair<String, Any>) {
        view?.end(name, *params)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class StatisticsViewModule {
    @BindsOptionalOf
    internal abstract fun viewNulls(): StatisticsView?
}
