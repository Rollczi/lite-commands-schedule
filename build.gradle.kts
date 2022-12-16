plugins {
    id("java-library")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("xyz.jpenilla.run-paper") version "2.0.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.rollczi"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/public/") }
    maven { url = uri("https://repo.panda-lang.org/releases") }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
    implementation("net.kyori:adventure-platform-bukkit:4.2.0")
    implementation("net.kyori:adventure-text-minimessage:4.12.0")
    implementation("com.google.code.gson:gson:2.10")

    implementation("net.dzikoysk:cdn:1.14.1")

    implementation("dev.rollczi.litecommands:bukkit-adventure:2.7.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

bukkit {
    main = "dev.rollczi.litescheduledaction.LiteScheduledAction"
    apiVersion = "1.13"
    prefix = "LiteScheduledAction"
    author = "Rollczi"
    name = "LiteScheduledAction"
    version = "${project.version}"
}


tasks {
    runServer {
        minecraftVersion("1.19.3")
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("LiteScheduledAction v${project.version} (MC 1.17-1.19x).jar")

    exclude(
            "org/intellij/lang/annotations/**",
            "org/jetbrains/annotations/**",
            "org/checkerframework/**",
            "META-INF/**",
            "javax/**"
    )

    mergeServiceFiles()
    minimize()

    val prefix = "dev.rollczi.litescheduledaction.libs"
    listOf(
            "panda",
            "org.panda_lang",
            "net.dzikoysk",
            "dev.rollczi.litecommands",
            "net.kyori",
            "com.google.gson",
    ).forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
}
