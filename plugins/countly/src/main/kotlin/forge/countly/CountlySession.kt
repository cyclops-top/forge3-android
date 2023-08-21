package forge.countly

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ly.count.android.sdk.Countly
import top.cyclops.forge.statistics.StatisticsSession
import javax.inject.Inject
import javax.inject.Singleton

class CountlySession @Inject constructor() : StatisticsSession {
    private val session get() = Countly.sharedInstance().sessions()
    override fun start() {
        session?.beginSession()
    }

    override fun update() {
        session?.updateSession()
    }

    override fun end() {
        session?.endSession()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CountlySessionModule {

    @Singleton
    @Binds
    internal abstract fun session(session: CountlySession): StatisticsSession
}
