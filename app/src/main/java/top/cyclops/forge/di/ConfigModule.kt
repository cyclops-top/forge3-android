package top.cyclops.forge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import forge.common.ForgeConfig
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ConfigModule {
    @Provides
    @Singleton
    fun config(): ForgeConfig {
        return ForgeConfig("http://forge.cyclops.top/api/","android-app","123456")
    }
}