
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
}
repositories {
    google()
    mavenCentral()
}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-stdlib:1.9.21")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.21")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.21")
    }
}

kotlin {
    jvm("desktop")

    // Apply KSP to the desktop target
    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }

        val desktopMain by getting
        val desktopTest by getting

        desktopMain.dependencies {
            // Compose dependencies for desktop
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.desktop.currentOs)

            // Material icons extended
            implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.5.11")

            // UI Preview for @Preview annotation
            implementation("org.jetbrains.compose.ui:ui-tooling-preview:1.5.11")

            // Note: Material 3 Adaptive components are not available for desktop applications
            // They are Android-specific and have been removed

            // Room dependencies for local storage
            implementation(libs.room.runtime.jvm)

            // MongoDB driver dependency
            implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.11.0")
        }

        desktopTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.junit)
            implementation(libs.junit)
            implementation(libs.room.runtime.jvm)

        }
    }
}

// Configure KSP for Room
dependencies {
    add("kspDesktop", "androidx.room:room-compiler:2.7.1")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KMPDesktopApp"
            packageVersion = "1.0.0"

            val iconsRoot = project.file("desktop-icons")
            windows {
                iconFile.set(iconsRoot.resolve("icon-windows.ico"))
            }
            macOS {
                iconFile.set(iconsRoot.resolve("icon-mac.icns"))
            }
            linux {
                iconFile.set(iconsRoot.resolve("icon-linux.png"))
            }
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}
