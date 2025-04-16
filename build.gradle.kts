plugins {
    kotlin("jvm") version "1.9.23"
    id("org.jetbrains.compose") version "1.6.10"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.windows_x64)
    implementation("org.xerial:sqlite-jdbc:3.44.1.0")
    implementation("ch.qos.logback:logback-classic:1.5.18")
}

application {
    mainClass.set("MainKt")  // Ponto de entrada da aplicação
}


kotlin {
    jvmToolchain(17)
}




