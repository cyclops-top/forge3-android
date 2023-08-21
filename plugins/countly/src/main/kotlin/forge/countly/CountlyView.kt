package forge.countly

import android.util.Log
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ly.count.android.sdk.Countly
import top.cyclops.forge.statistics.StatisticsView
import javax.inject.Inject
import javax.inject.Singleton


class CountlyView @Inject constructor() : StatisticsView {
    private val view get() = Countly.sharedInstance().views()
    override fun start(name: String, vararg params: Pair<String, Any>) {
        if (params.isEmpty()) {
            view.recordView(name)
        } else {
            view.recordView(name, mapOf(*params))
        }
    }

    override fun end(name: String, vararg params: Pair<String, Any>) {
        Log.d("countly", "countly not support end view")
    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class CountlyViewModule {

    @Singleton
    @Binds
    internal abstract fun view(view: CountlyView): StatisticsView
}
