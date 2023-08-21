package top.cyclops.forge.statistics

import kotlin.time.Duration

interface StatisticsEvent {
    fun event(id: String, vararg params: Pair<String,Any>)
    fun start(id: String)
    fun end(id: String)
    fun time(id: String,  time: Duration,vararg params: Pair<String,Any>)
}
