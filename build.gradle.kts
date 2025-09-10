plugins {
    java
    id("io.quarkus") version "3.26.1"
    id("maven-publish")
}

repositories {
    mavenCentral()
    mavenLocal()
}

publishing {
    // ./gradlew publishToMavenLocal
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks["jar"]) // fallback to jar task if java component is unavailable
            groupId = "com.adcb.ms"
            artifactId = "validator"
            version = "1.0.0-SNAPSHOT"
        }
    }
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("io.quarkus:quarkus-bom:3.26.1"))
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    implementation("io.quarkus:quarkus-jdbc-h2")
}

group = "com.adcb.ms"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}
