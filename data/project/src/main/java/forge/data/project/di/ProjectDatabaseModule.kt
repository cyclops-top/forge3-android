package forge.data.project.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import forge.data.project.local.ProjectDao
import forge.data.project.local.ProjectDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProjectDatabaseModule {

    @Singleton
    @Provides
    internal fun database(@ApplicationContext context: Context): ProjectDatabase {
        return Room.inMemoryDatabaseBuilder(context, ProjectDatabase::class.java)
            .enableMultiInstanceInvalidation()
            .build()
    }

    @Singleton
    @Provides
    internal fun projectDao(db: ProjectDatabase): ProjectDao = db.projectDao()

}