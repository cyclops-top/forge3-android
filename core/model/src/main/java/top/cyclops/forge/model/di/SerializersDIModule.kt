package top.cyclops.forge.model.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.modules.SerializersModule
import javax.inject.Singleton

/**
 *
 * @author justin on 2022/12/3
 */
@InstallIn(SingletonComponent::class)
@Module
object SerializersDIModule {
    @Provides
    @Singleton
    fun serializersModule() = SerializersModule {

    }
}