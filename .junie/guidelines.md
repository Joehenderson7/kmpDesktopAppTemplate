# KMP Desktop App Development Guidelines

This document provides essential information for developers working on this Kotlin Multiplatform Desktop application project.

## Build/Configuration Instructions

### Technology Stack

- **UI Framework**: Jetpack Compose with Material 3
  - The application uses Compose for Desktop to create a modern, declarative UI
  - Material 3 components and theming are implemented for consistent design
  - Material 3 Adaptive components are used for responsive layouts
  - Window size classes should be used for making layout decisions

### Dependencies

#### Compose Bill of Materials (BOM)

The project uses the Compose Bill of Materials (BOM) version 1.8.2 to manage Compose dependencies versions:

```kotlin
implementation(platform("androidx.compose:compose-bom:1.8.2"))
```

Benefits of using the Compose BOM:
- Ensures all Compose dependencies use compatible versions
- Simplifies dependency management by centralizing version control
- Reduces the risk of version conflicts between Compose libraries
- Makes it easier to upgrade Compose versions across the project

Key dependencies in the project:
- Jetpack Compose Material 3 (`compose.material3`)
- Compose UI core libraries (`compose.ui`)
- Material 3 Adaptive components for responsive layouts
  - Material 3 Adaptive core (`adaptive`)
  - Material 3 Adaptive layout (`adaptive-layout`)
  - Material 3 Adaptive navigation (`adaptive-navigation`)
- Material Icons Extended for a comprehensive icon set

### Local Storage

#### Room Database for Local Persistence

The project uses Room database for local data persistence. Room provides an abstraction layer over SQLite that allows for more robust database access while harnessing the full power of SQLite.

##### Room Dependencies

Add these dependencies to your `composeApp/build.gradle.kts` file:

```kotlin
// Room dependencies
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")

// For Kotlin Symbol Processing (KSP)
plugins {
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
}
```

##### Room Components

1. **Entity**: Annotated data classes that represent tables in the database
   ```kotlin
   @Entity(tableName = "settings")
   data class SettingsEntity(
       @PrimaryKey val id: String,
       val value: String
   )
   ```

2. **DAO (Data Access Object)**: Interfaces that define database operations
   ```kotlin
   @Dao
   interface SettingsDao {
       @Query("SELECT * FROM settings WHERE id = :id")
       suspend fun getSettingById(id: String): SettingsEntity?

       @Insert(onConflict = OnConflictStrategy.REPLACE)
       suspend fun insertSetting(setting: SettingsEntity)
   }
   ```

3. **Database**: Abstract class that extends RoomDatabase
   ```kotlin
   @Database(entities = [SettingsEntity::class], version = 1)
   abstract class AppDatabase : RoomDatabase() {
       abstract fun settingsDao(): SettingsDao

       companion object {
           @Volatile
           private var INSTANCE: AppDatabase? = null

           fun getDatabase(context: Context): AppDatabase {
               return INSTANCE ?: synchronized(this) {
                   val instance = Room.databaseBuilder(
                       context,
                       AppDatabase::class.java,
                       "app_database"
                   ).build()
                   INSTANCE = instance
                   instance
               }
           }
       }
   }
   ```

4. **Repository**: Class that abstracts data operations
   ```kotlin
   class SettingsRepository(private val settingsDao: SettingsDao) {
       suspend fun getSetting(id: String, defaultValue: String): String {
           val setting = settingsDao.getSettingById(id)
           return setting?.value ?: defaultValue
       }

       suspend fun saveSetting(id: String, value: String) {
           settingsDao.insertSetting(SettingsEntity(id, value))
       }
   }
   ```

##### Using Room in the Application

1. Initialize the database in your application:
   ```kotlin
   val database = AppDatabase.getDatabase(applicationContext)
   val settingsRepository = SettingsRepository(database.settingsDao())
   ```

2. Read and write data using the repository:
   ```kotlin
   // Read setting
   val value = settingsRepository.getSetting("split_position", "0.5")

   // Save setting
   settingsRepository.saveSetting("split_position", "0.7")
   ```

3. For UI components, use `rememberCoroutineScope()` to launch database operations:
   ```kotlin
   val coroutineScope = rememberCoroutineScope()

   // Save setting when value changes
   onSplitChanged = { newValue ->
       splitPosition = newValue
       coroutineScope.launch {
           settingsRepository.saveSetting("split_position", newValue.toString())
       }
   }
   ```

#### MongoDB Integration

MongoDB integration is prepared but requires proper version configuration. Use the following dependency:

```kotlin
implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.11.0")
```

## Testing Information

### Test Configuration

1. Tests are located in the `composeApp/src/desktopTest/kotlin` directory
2. The project uses the Kotlin Test framework with JUnit runner
3. Dependencies are configured in the `composeApp/build.gradle.kts` file:
   ```kotlin
   desktopTest.dependencies {
       implementation(libs.kotlin.test)
       implementation(libs.kotlin.test.junit)
       implementation(libs.junit)
   }
   ```

### Running Tests

Tests can be run using Gradle:

```bash
./gradlew :composeApp:desktopTest
```

Or through your IDE's test runner (recommended for development).

### Writing New Tests

1. Create test classes in the `composeApp/src/desktopTest/kotlin` directory
2. Use the `@Test` annotation from `kotlin.test` to mark test methods
3. Follow the Given-When-Then pattern for clear test structure
4. Use assertions from `kotlin.test` like `assertEquals`, `assertTrue`, etc.

### Example Test

```kotlin
class SimpleTest {

    // Use @Test annotation from kotlin.test
    fun testSimpleAddition() {
        // Given
        val a = 2
        val b = 3

        // When
        val result = a + b

        // Then
        // Use assertEquals from kotlin.test
        assert(result == 5) { "Simple addition should work correctly" }
    }
}
```

## Development Information

### Architecture

The project follows the MVVM (Model-View-ViewModel) architecture:

- **View**: Compose UI components in the `ui` package
  - Feature-specific UI components are organized in the `ui/features` directory
  - Navigation components are in the `ui/navigation` directory
  - Theme definitions are in the `ui/theme` directory

- **ViewModel**: Classes that handle UI logic and state
  - Located in feature-specific packages (e.g., `ui/features/projects/ProjectsViewModel.kt`)
  - Responsible for processing user actions and providing UI state

- **Model**: Data and business logic (to be implemented)
  - Will include data classes, repositories, and data sources
  - Room database will be used for local persistence

### Navigation

The application uses a navigation system with defined destinations:
- Navigation components are in the `ui/navigation` package
- `NavigationDestinations` class defines the available screens
- `AppNavHost` handles navigation between screens

### Input Support

- Support input beyond touch:
  - Implement keyboard navigation and shortcuts for all interactive elements
  - Ensure proper focus management for keyboard navigation
  - Support mouse interactions (click, hover, scroll) with appropriate visual feedback
  - Consider accessibility features like screen readers and keyboard-only navigation

### Code Style

- Follow Kotlin coding conventions
- Use descriptive naming for classes, functions, and variables
- Organize code by feature in the `ui/features` directory
- Keep UI components focused on presentation, with logic in ViewModels
- Use composable functions for UI components with clear parameter definitions

### Persisting UI State

The application uses a `SettingsManager` class to persist UI state, such as split pane positions, between application reloads. This ensures that the user's UI customizations are remembered.

#### SettingsManager

The `SettingsManager` class provides methods for reading and writing settings to a properties file:

```kotlin
// Get the settings manager instance
val settingsManager = SettingsManager.getInstance()

// Read a setting
val splitPosition = settingsManager.getFloat("splitPosition", 0.5f) // Default value is 0.5f

// Write a setting
settingsManager.setFloat("splitPosition", 0.7f)
```

#### Persisting Split Pane Positions

The split pane positions are persisted using the `SettingsManager`:

1. When the application starts, the split pane positions are loaded from the settings:
   ```kotlin
   var splitPosition by remember { 
       mutableStateOf(settingsManager.getFloat("splitPosition", 0.5f))
   }
   ```

2. When the split pane positions change, they are saved to the settings:
   ```kotlin
   onSplitChanged = { 
       splitPosition = it
       coroutineScope.launch {
           settingsManager.setFloat("splitPosition", it)
       }
   }
   ```

3. When the application is closed, the split pane positions are saved to ensure no changes are lost:
   ```kotlin
   DisposableEffect(Unit) {
       onDispose {
           settingsManager.setFloat("splitPosition", splitPosition)
       }
   }
   ```

### Future Development

- Complete the navigation system
- Add proper error handling and loading states
- Consider implementing dependency injection
- Implement Room database for more complex data storage needs
