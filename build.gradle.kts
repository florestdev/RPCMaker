plugins {
    id("java")
    id("com.gradleup.shadow") version "9.3.1"
}

group = "ru.florestdev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
    maven(url = "https://jcenter.bintray.com/")
    maven(
        url = uri("https://maven.firstdark.dev/releases")
    )
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.github.JnCrMx:discord-game-sdk4j:1.0.0")
}

tasks.test {
    useJUnitPlatform()
}