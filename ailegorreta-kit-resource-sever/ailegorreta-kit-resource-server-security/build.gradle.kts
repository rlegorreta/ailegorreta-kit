plugins {
    `java-library`
    `maven-publish`
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("java")
    kotlin("jvm") version "1.8.21"
}

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

extra["springCloudVersion"] = "2022.0.3"
extra["ailegorretaVersion"] = "2.0.0"
extra["jacksonVersion"] = "2.15.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")

    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.slf4j:slf4j-api")

    implementation("com.fasterxml.jackson.core:jackson-databind:${property("jacksonVersion")}")
    implementation("jakarta.servlet:jakarta.servlet-api")

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
            artifactId = "ailegorreta-kit-resource-server-security"
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
                name.set("ailegorreta-kit-resource-server-security")
                description.set("Libraries to secure a back-end microservice (i.e., client_credentials")
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

        /*
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/rlegorreta/ailegorreta-kit")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: "rlegorreta"
                password = System.getenv("GITHUB_TOKEN")
            }
        }

         */
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
