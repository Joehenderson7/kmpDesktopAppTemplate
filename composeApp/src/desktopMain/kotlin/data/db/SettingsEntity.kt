package data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class for storing application settings in the Room database.
 * 
 * @property id The unique identifier for the setting
 * @property value The value of the setting as a string
 */
@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val id: String,
    val value: String
)