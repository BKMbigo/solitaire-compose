import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
}

group = "com.github.bkmbigo.solitaire.webapp.wasmApp"
version = "unspecified"

val copyWasmResources = tasks.create("copyWasmResourcesWorkaround", Copy::class.java) {
    from(project(":common").file("src/commonMain/resources"))
    into("build/processedResources/wasmJs/main")
}

afterEvaluate {
//    project.tasks.getByName("wasmJsProcessResources").finalizedBy(copyWasmResources)
}

kotlin {
    wasmJs {
        moduleName = "game"
        browser {
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).copy(
                    open = mapOf(
                        "app" to mapOf(
                            "name" to "google-chrome",
                        )
                    )
                )
            }
        }
        binaries.executable()
    }
    sourceSets {
        val wasmJsMain by getting {
            dependencies {
                implementation(project(":common"))

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
            }
        }
    }
}



compose {
    experimental {
        web.application {}
    }
    kotlinCompilerPlugin.set(libs.versions.compose.multiplatform.compiler)
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=${libs.versions.kotlin.get()}")
}
