package data

import java.io.File
import java.util.Properties

/**
 * Manager class for handling application settings.
 * This class provides methods for reading and writing settings to a properties file.
 */
class SettingsManager {
    private val settingsFile: File
    private val properties: Properties = Properties()
    
    init {
        // Create the settings directory if it doesn't exist
        val settingsDir = File(System.getProperty("user.home"), ".kmpDesktopApp")
        if (!settingsDir.exists()) {
            settingsDir.mkdirs()
        }
        
        // Create the settings file if it doesn't exist
        settingsFile = File(settingsDir, "settings.properties")
        if (settingsFile.exists()) {
            // Load existing settings
            settingsFile.inputStream().use { properties.load(it) }
        }
    }
    
    /**
     * Get a setting value as a string.
     * 
     * @param key The key of the setting to retrieve
     * @param defaultValue The default value to return if the setting doesn't exist
     * @return The setting value, or the default value if the setting doesn't exist
     */
    fun getString(key: String, defaultValue: String): String {
        return properties.getProperty(key, defaultValue)
    }
    
    /**
     * Get a setting value as a float.
     * 
     * @param key The key of the setting to retrieve
     * @param defaultValue The default value to return if the setting doesn't exist
     * @return The setting value as a float, or the default value if the setting doesn't exist
     */
    fun getFloat(key: String, defaultValue: Float): Float {
        val value = properties.getProperty(key)
        return if (value != null) {
            try {
                value.toFloat()
            } catch (e: NumberFormatException) {
                defaultValue
            }
        } else {
            defaultValue
        }
    }
    
    /**
     * Set a setting value.
     * 
     * @param key The key of the setting to set
     * @param value The value to set
     */
    fun setString(key: String, value: String) {
        properties.setProperty(key, value)
        saveSettings()
    }
    
    /**
     * Set a setting value as a float.
     * 
     * @param key The key of the setting to set
     * @param value The value to set
     */
    fun setFloat(key: String, value: Float) {
        properties.setProperty(key, value.toString())
        saveSettings()
    }
    
    /**
     * Save the settings to the properties file.
     */
    private fun saveSettings() {
        settingsFile.outputStream().use { properties.store(it, "KMP Desktop App Settings") }
    }
    
    companion object {
        @Volatile
        private var INSTANCE: SettingsManager? = null
        
        /**
         * Get the singleton instance of the SettingsManager.
         * If the instance doesn't exist, it will be created.
         * 
         * @return The SettingsManager instance
         */
        fun getInstance(): SettingsManager {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingsManager()
                INSTANCE = instance
                instance
            }
        }
    }
}