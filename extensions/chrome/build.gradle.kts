plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":common"))

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)

                implementation(npm("chrome-types", "0.1.237"))
            }
        }
    }
}

val copyJsResources = tasks.create("copyJsResourcesWorkaround", Copy::class.java) {
    from(project(":common").file("src/commonMain/resources"))
    into("build/processedResources/js/main")
}

afterEvaluate {
//    project.tasks.getByName("jsProcessResources").finalizedBy(copyJsResources)
}

compose {
    experimental {
        web.application {}
    }
    kotlinCompilerPlugin.set(libs.versions.compose.multiplatform.compiler)
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=${libs.versions.kotlin.get()}")
}
