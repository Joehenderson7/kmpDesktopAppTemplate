
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}
repositories {
    google()
    mavenCentral()
}

kotlin {
    jvm("desktop")

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

            // MongoDB driver dependency - commented out until version is specified
            // implementation("org.mongodb:mongodb-driver-kotlin-coroutine")
        }

        desktopTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.junit)
            implementation(libs.junit)
        }
    }
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
