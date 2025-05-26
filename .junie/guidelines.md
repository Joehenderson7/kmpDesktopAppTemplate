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

- Room database is planned for local data persistence
- MongoDB integration is prepared but requires proper version configuration

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

### Future Development

- Implement Room database for local storage
- Complete the navigation system
- Add proper error handling and loading states
- Consider implementing dependency injection
