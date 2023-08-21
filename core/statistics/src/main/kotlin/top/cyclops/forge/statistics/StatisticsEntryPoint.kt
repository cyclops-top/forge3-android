package top.cyclops.forge.statistics

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface StatisticsEntryPoint {
    fun event(): StatisticsEvent
    fun nullableSession(): Optional<StatisticsSession>
    fun nullableView(): Optional<StatisticsView>
}

internal fun StatisticsEntryPoint.session(): StatisticsSession {
    return StatisticsSessionDelegate(nullableSession().getOrNull())
}


internal fun StatisticsEntryPoint.view(): StatisticsView {
    return StatisticsViewDelegate(nullableView().getOrNull())
}
