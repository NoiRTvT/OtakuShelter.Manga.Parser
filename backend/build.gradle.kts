plugins {
    application
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.3.60"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to application.mainClassName
            )
        )
    }
}

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    compile("ch.qos.logback:logback-classic:$logback_version")
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("io.ktor:ktor-client-core:$ktor_version")
    compile("io.ktor:ktor-client-core-jvm:$ktor_version")
    compile("io.ktor:ktor-client-apache:$ktor_version")
    compile("io.ktor:ktor-server-core:$ktor_version")
    compile("io.ktor:ktor-server-host-common:$ktor_version")
    compile("io.ktor:ktor-serialization:$ktor_version")
    compile("io.ktor:ktor-client-core:$ktor_version")
    compile("org.jetbrains.exposed:exposed:0.17.7")
    compile("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.14.0")
    compile("org.postgresql:postgresql:42.2.8")
    compile("org.jsoup:jsoup:1.12.1")

    testCompile("io.ktor:ktor-server-tests:$ktor_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

task("devApp") {
    dependsOn("run")
}

task("buildApp") {
    dependsOn("build")
    finalizedBy("moveApp")
}

task<Copy>("moveApp") {
    from(file("build/libs/backend-all.jar")).into("../build")
    rename {
        it.replace("backend-all", "application")
    }
}

task<Delete>("cleanApp") {
    delete(fileTree("resources/static"))
    delete("build")
}
