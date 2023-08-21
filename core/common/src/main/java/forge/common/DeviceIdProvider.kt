package forge.common

import dagger.BindsOptionalOf
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

interface DeviceIdProvider {
    suspend fun getDeviceId(): String
}

@Module
@InstallIn(SingletonComponent::class)
internal interface DeviceIdProviderModule {
    @BindsOptionalOf
    fun provider(): DeviceIdProvider?
}
