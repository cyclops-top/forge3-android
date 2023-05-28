package forge.data.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import forge.common.AuthEventHandler
import forge.data.auth.interal.DefaultUnauthorizedEventHandler
import javax.inject.Singleton

/**
 *
 * @author justin on 2023/5/27
 */
@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun repo(): AuthEventHandler {
        return DefaultUnauthorizedEventHandler()
    }
}
