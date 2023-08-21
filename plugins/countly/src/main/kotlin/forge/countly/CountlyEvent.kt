package forge.countly

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ly.count.android.sdk.Countly
import top.cyclops.forge.statistics.StatisticsEvent
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration

class CountlyEvent @Inject constructor() : StatisticsEvent {

    private val events get() = Countly.sharedInstance().events()

    override fun event(id: String, vararg params: Pair<String, Any>) {
        if (params.isEmpty()) {
            events.recordEvent(id)
        } else {
            events.recordEvent(id, mapOf(*params))
        }
    }

    override fun start(id: String) {
        events.startEvent(id)
    }

    override fun end(id: String) {
        events.endEvent(id)
    }

    override fun time(id: String, time: Duration, vararg params: Pair<String, Any>) {
        events.recordPastEvent(id, mapOf(*params), time.inWholeMilliseconds)
    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class CountlyEventModule {
    @Singleton
    @Binds
    internal abstract fun countlyEvent(repo: CountlyEvent): StatisticsEvent
}
