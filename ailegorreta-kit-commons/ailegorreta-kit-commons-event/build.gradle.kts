plugins {
    `java-library`
    `maven-publish`
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("java")
    kotlin("jvm") version "1.8.21"}

group = "com.ailegorreta"
version = "2.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/snapshot") }
}

extra["springCloudVersion"] = "2022.0.3-SNAPSHOT"
extra["ailegorretaVersion"] = "2.0.0"

dependencies {
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.slf4j:slf4j-api")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.ailegorreta"
            artifactId = "ailegorreta-kit-commons-event"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("ailegorreta-commons-event")
                description.set("DTO to be used by events")
                url.set("http://www.legosoft.com.mx")
                properties.set(mapOf(
                    "version" to "2.0.0"
                ))
                developers {
                    developer {
                        id.set("rlh")
                        name.set("Ricardo Legorreta")
                        email.set("rlegorreta@legosoft.com.mx")
                    }
                }
            }

        }
    }

    repositories {
        mavenLocal()

        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/rlegorreta/ailegorreta-kit")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: "rlegorreta"
                password = System.getenv("GITHUB_TOKEN") ?: "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"
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}