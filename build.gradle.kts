// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.gradleIntelliJPlugin) apply false
}
true // Needed to make the Suppress annotation work for the plugins block

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }

    configurations.all {
        val conf = this
        // Currently it's necessary to make the android build work properly
        conf.resolutionStrategy.eachDependency {
            val isWasm = conf.name.contains("wasm", true)
            val isJs = conf.name.contains("js", true)
            val isComposeGroup = requested.module.group.startsWith("org.jetbrains.compose")
            val isComposeCompiler = requested.module.group.startsWith("org.jetbrains.compose.compiler")
            if (isComposeGroup && !isComposeCompiler && !isWasm && !isJs) {
                useVersion(libs.versions.compose.multiplatform.stable.get())
            }
            if (requested.module.name.startsWith("kotlin-stdlib")) {
                useVersion(libs.versions.kotlin.get())
            }
            if(requested.module.name.contains("kotlinx-coroutines-core") && isWasm) {
                useVersion(libs.versions.kotlinx.coroutines.wasm.get())
            }
            if(requested.module.name.contains("kotlinx-datetime") && isWasm) {
                useVersion(libs.versions.kotlinx.datetime.wasm.get())
            }
        }
    }
}
