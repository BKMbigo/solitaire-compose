plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
}

group = "com.github.bkmbigo.solitaire.visualstudiocode"
version = "unspecified"

val copyJsResources = tasks.create("copyJsResourcesWorkaround", Copy::class.java) {
    from(project(":common").file("src/commonMain/resources"))
    into("build/processedResources/js/main")
}

afterEvaluate {
//    project.tasks.getByName("jsProcessResources").finalizedBy(copyJsResources)
}

kotlin {
    js(IR) {
        moduleName = "solitaire"
        browser()
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
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
        web {
            application {}
        }
    }
    kotlinCompilerPlugin.set(libs.versions.compose.multiplatform.wasm)
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=${libs.versions.kotlin.get()}")
}
