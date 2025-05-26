package data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object (DAO) for the settings table.
 * This interface defines the methods for accessing the settings data in the Room database.
 */
@Dao
interface SettingsDao {
    /**
     * Get a setting by its ID.
     * 
     * @param id The ID of the setting to retrieve
     * @return The setting with the specified ID, or null if not found
     */
    @Query("SELECT * FROM settings WHERE id = :id")
    suspend fun getSettingById(id: String): SettingsEntity?
    
    /**
     * Insert or update a setting.
     * If a setting with the same ID already exists, it will be replaced.
     * 
     * @param setting The setting to insert or update
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting: SettingsEntity)
    
    /**
     * Get all settings.
     * 
     * @return A list of all settings in the database
     */
    @Query("SELECT * FROM settings")
    suspend fun getAllSettings(): List<SettingsEntity>
}