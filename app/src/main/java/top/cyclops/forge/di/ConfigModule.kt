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
        return ForgeConfig("http://192.168.31.175:8080/","android-app","123456")
    }
}