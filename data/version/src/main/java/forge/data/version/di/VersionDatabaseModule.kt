package forge.data.version.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import forge.data.version.local.VersionDao
import forge.data.version.local.VersionDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object VersionDatabaseModule {

    @Singleton
    @Provides
    internal fun database(@ApplicationContext context: Context): VersionDatabase {
        return Room.inMemoryDatabaseBuilder(context, VersionDatabase::class.java)
            .enableMultiInstanceInvalidation()
            .build()
    }

    @Singleton
    @Provides
    internal fun projectDao(db: VersionDatabase): VersionDao = db.versionDao()

}