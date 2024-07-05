import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlinx.kover") version "0.8.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
}

group = "visualizer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")
    google()
}


dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    val nav_version = "2.8.0-alpha02"
    implementation("org.xerial", "sqlite-jdbc", "3.41.2.1")
    implementation("org.jetbrains.androidx.navigation:navigation-compose:$nav_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("com.github.uhh-lt:chinese-whispers:-SNAPSHOT")

    //neo4j
    implementation("org.neo4j.driver", "neo4j-java-driver", "5.6.0")

    // logging
    implementation("io.github.microutils", "kotlin-logging-jvm", "2.0.6")
    implementation("org.slf4j", "slf4j-simple", "1.7.29")
    implementation(compose.components.resources)

    testImplementation(kotlin("test"))
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "GraphVisualizer"
            packageVersion = "1.0.0"
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}

tasks.withType<Jar> {

    manifest {
        attributes["Main-Class"] = "MainKt"
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    exclude("src/main/resources")
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

sourceSets {
    main {
        resources {
            srcDir("src/main/resources")
        }
    }
}
