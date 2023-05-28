package forge.data.version.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import forge.data.RoomConverters
import forge.model.Version

@TypeConverters(RoomConverters::class)
@Database(
    entities = [Version::class],
    exportSchema = false,
    version = 1
)
internal abstract class VersionDatabase : RoomDatabase() {
    abstract fun versionDao(): VersionDao
}