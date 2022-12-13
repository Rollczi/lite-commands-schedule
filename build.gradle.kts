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
    maven { url = uri("https://repo.panda-lang.org/releases") }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
    implementation("net.kyori:adventure-platform-bukkit:4.2.0")
    implementation("net.kyori:adventure-text-minimessage:4.12.0")

    implementation("net.dzikoysk:cdn:1.14.1")

    implementation("dev.rollczi.litecommands:bukkit-adventure:2.7.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

bukkit {
    main = "com.eternalcode.core.EternalCore"
    apiVersion = "1.13"
    prefix = "EternalCore"
    author = "EternalCodeTeam"
    name = "EternalCore"
    description = "All the most important server functions in one!"
    version = "${project.version}"
    softDepend = listOf("PlaceholderAPI")
    libraries = listOf(
            "org.postgresql:postgresql:42.5.1",
            "com.h2database:h2:2.1.214",
            "com.j256.ormlite:ormlite-jdbc:6.1",
            "com.zaxxer:HikariCP:5.0.1",
            "org.mariadb.jdbc:mariadb-java-client:3.1.0"
    )
}


tasks {
    runServer {
        minecraftVersion("1.19.2")
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("EternalCore v${project.version} (MC 1.17-1.19x).jar")

    exclude(
            "org/intellij/lang/annotations/**",
            "org/jetbrains/annotations/**",
            "org/checkerframework/**",
            "META-INF/**",
            "javax/**"
    )

    mergeServiceFiles()
    minimize()

    val prefix = "com.eternalcode.core.libs"
    listOf(
            "panda",
            "org.panda_lang",
            "org.bstats",
            "net.dzikoysk",
            "dev.rollczi",
            "net.kyori",
            "io.papermc.lib",
            "dev.triumphteam",
            "org.slf4j",
            "com.google.gson",
            "com.eternalcode.containers"
    ).forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
}
