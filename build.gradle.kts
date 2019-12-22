plugins {
    kotlin("jvm") version "1.3.50"
}

group = "com.otakushelter"
version = "0.0.1-SNAPSHOT"

subprojects {
    repositories {
        mavenLocal()
        jcenter()
        maven { url = uri("https://kotlin.bintray.com/ktor") }
    }
}

task("devApp") {
    dependsOn("backend:devApp")
}

task("cleanApp") {
    delete("build")
    dependsOn("backend:cleanApp")
}

task("buildApp") {
    dependsOn("cleanApp")
    finalizedBy("backend:buildApp")
}