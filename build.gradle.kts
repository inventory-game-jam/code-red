plugins {
    kotlin("jvm") version "2.0.20"
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("com.gradleup.shadow") version "8.3.0"
    kotlin("plugin.serialization") version "2.0.0"
}

group = "com.github.inventorygamejam"
version = "0.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://maven.radsteve.net/public")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.noxcrew.com/public")
}

dependencies {
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:2.19.0")
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:2.19.0")
    implementation("org.incendo:cloud-paper:2.0.0-SNAPSHOT")
    implementation("org.incendo:cloud-kotlin-extensions:2.0.0-SNAPSHOT")
    implementation("org.incendo:cloud-kotlin-coroutines:2.0.0-SNAPSHOT")
    implementation("org.apache.commons:commons-lang3:3.0")
    implementation("net.radstevee.packed:packed-core:0.5.0")
    implementation("net.radstevee.packed:packed-negative-spaces:0.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
    implementation("io.ktor:ktor-client-core:2.3.12")
    implementation("io.ktor:ktor-client-cio:2.3.12")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.12")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.12")
    implementation("com.noxcrew.interfaces:interfaces:1.2.1-SNAPSHOT") {
        exclude("io.papermc.paper", "paper-api")
    }

    paperweight.paperDevBundle("1.21.1-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(21)
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
    dependsOn(tasks.processResources)
}

tasks.withType<xyz.jpenilla.runtask.task.AbstractRun> {
    javaLauncher =
        javaToolchains.launcherFor {
            // vendor = JvmVendorSpec.JETBRAINS
            languageVersion = JavaLanguageVersion.of(21)
        }
    // jvmArgs("-XX:+AllowEnhancedClassRedefinition", "-XX:+AllowRedefinitionToAddDeleteMethods")
}
