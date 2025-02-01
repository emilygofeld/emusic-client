import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.0.20"
}

object Versions {
    const val ktor_version = "3.0.0"
    const val koin_version = "4.0.0"
}


kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting {
            resources.srcDirs("src/desktopMain/res")
        }
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // serialization
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

            // ktor client
            implementation("io.ktor:ktor-client-core:${Versions.ktor_version}")
            implementation("io.ktor:ktor-client-cio:${Versions.ktor_version}")

            // ktor content negotiation for JSON serialization
            implementation("io.ktor:ktor-client-content-negotiation:${Versions.ktor_version}")
            implementation("io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor_version}")

            // settings (key-value pair local save)
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.2.0")

            // koin dependency injection
            implementation("io.insert-koin:koin-core:${Versions.koin_version}")
            implementation("io.insert-koin:koin-compose-viewmodel:${Versions.koin_version}")

            // icons
            implementation(compose.materialIconsExtended)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}


compose.desktop {
    application {
        mainClass = "org.emily.project.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.emily.project"
            packageVersion = "1.0.0"
        }
    }
}
