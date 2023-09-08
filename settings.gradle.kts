pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        mavenCentral()
    }
}
rootProject.name = "Solitaire"
include(":app")
include(":common")
include(":desktop")
include("ide")
