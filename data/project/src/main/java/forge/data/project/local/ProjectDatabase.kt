package forge.data.project.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import forge.data.RoomConverters

@TypeConverters(RoomConverters::class)
@Database(
    entities = [ProjectLocal::class],
    exportSchema = false,
    version = 1
)
internal abstract class ProjectDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
}