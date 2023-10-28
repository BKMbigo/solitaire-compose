pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
        google()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://androidx.dev/storage/compose-compiler/repository")
        mavenCentral()
    }
}
rootProject.name = "Solitaire"
include(":app")
include(":common")
include(":desktop")
include(":extensions:chrome")
include(":ide-plugins:intellij")
include("ide-plugins:visualstudiocode")
include(":webapp:wasmApp")
include(":webapp:jsApp")
