
plugins {
    `java-library`
    `maven-publish`
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("java")
    kotlin("jvm") version "1.8.21"
}

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

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.jar {
    manifest {
        attributes(mapOf("Implementation-Title" to project.name,
                         "Implementation-Version" to project.version))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.ailegorreta"
            artifactId = "ailegorreta-kit-commons-utils"
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
                name.set("ailegorreta-commons-utilsxxx")
                description.set("General utilities for any micro.service")
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
            url = uri("https://maven.pkg.github.com/rlegorreta/ailegorreta-kit/ailegorreta-kit-commons-utils")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: "rlegorreta"
                password = System.getenv("GITHUB_TOKEN") ?: "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"
            }
        }
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}