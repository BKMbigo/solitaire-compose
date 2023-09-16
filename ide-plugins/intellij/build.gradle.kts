plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.gradleIntelliJPlugin)
}

group = "com.github.bkmbigo.solitaire"
version = "unspecified"

intellij {
    pluginName.set("Solitaire Compose")
    version.set("2022.3")
    type.set("IC")
}

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation(compose.material3)
    implementation(compose.runtime)
    implementation(compose.desktop.common)
    implementation(compose.desktop.linux_x64)
    implementation(compose.desktop.linux_arm64)
    implementation(compose.desktop.windows_x64)
    implementation(compose.desktop.macos_x64)
    implementation(compose.desktop.macos_arm64)

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    patchPluginXml {
        version.set("0.0.0-beta.3")

        sinceBuild.set("223")
        untilBuild.set("232.*")
    }
}
