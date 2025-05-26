package data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

/**
 * The Room database for the application.
 * This class provides access to the DAOs for the database.
 */
@Database(entities = [SettingsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Get the DAO for accessing settings data.
     * 
     * @return The SettingsDao instance
     */
    abstract fun settingsDao(): SettingsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Get the singleton instance of the database.
         * If the instance doesn't exist, it will be created.
         * 
         * @return The AppDatabase instance
         */
        fun getDatabase(): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val databaseDir = File(System.getProperty("user.home"), ".kmpDesktopApp")
                if (!databaseDir.exists()) {
                    databaseDir.mkdirs()
                }

                val databaseFile = File(databaseDir, "app_database.db")

                val instance = Room.databaseBuilder<AppDatabase>(
                    databaseFile.absolutePath
                ) {
                    AppDatabase::class.java.newInstance()
                }.build()

                INSTANCE = instance
                instance
            }
        }
    }
}
