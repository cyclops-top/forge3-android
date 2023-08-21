package forge.countly

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.migration.OptionalInject
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import forge.common.DeviceIdProvider
import forge.initialization.Initialization
import ly.count.android.sdk.Countly
import ly.count.android.sdk.CountlyConfig
import java.util.Optional
import javax.inject.Inject
import javax.inject.Singleton

@OptionalInject
class CountlyInitialization @Inject constructor(private val deviceIdProvider: Optional<DeviceIdProvider>) :
    Initialization {
    override val name: String = "countly"
    override suspend fun init(context: Context): Boolean {
        Countly.sharedInstance().init(
            CountlyConfig(
                context.applicationContext,
                "cd6ea08bb1e9e6ceea7505745a1dac9dac8826a8",
                "https://countly.cyclops.top"
            ).apply {
                if (deviceIdProvider.isPresent) {
                    setDeviceId(deviceIdProvider.get().getDeviceId())
                }
                enableManualSessionControl()
                enableCrashReporting()
                enableTemporaryDeviceIdMode()
                setViewTracking(true)
                setLoggingEnabled(true)
            }
        )
        return true
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CountlyInitializationModule {

    @Singleton
    @Binds
    @IntoSet
    internal abstract fun initialization(repo: CountlyInitialization): Initialization
}
