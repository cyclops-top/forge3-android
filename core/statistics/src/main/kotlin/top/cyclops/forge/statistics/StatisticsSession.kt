package top.cyclops.forge.statistics

import dagger.BindsOptionalOf
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

interface StatisticsSession {
    fun start()
    fun update()
    fun end()
}


internal class StatisticsSessionDelegate(private val session: StatisticsSession?) :
    StatisticsSession {
    override fun start() {
        session?.start()
    }

    override fun update() {
        session?.update()
    }

    override fun end() {
        session?.end()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface StatisticsSessionModule {

    @BindsOptionalOf
    fun session(): StatisticsSession?
}
