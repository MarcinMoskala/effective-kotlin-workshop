import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")

    // collections access list
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.7.4")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")

    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

// config JVM target to 1.8 for kotlin compilation tasks
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}
