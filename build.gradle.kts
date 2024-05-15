plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    id("org.jetbrains.kotlinx.kover") version "0.7.6"
}

dependencies{
    kover(project(":composeApp"))
    kover(project(":lib"))
}
