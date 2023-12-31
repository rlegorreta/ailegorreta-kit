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

extra["springCloudVersion"] = "2022.0.3-SNAPSHOT"
extra["ailegorretaVersion"] = "2.0.0"
extra["vaadinVersion"] = "24.1.0"
extra["vok-framework-vokdbVersion"] = "0.15.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")

    implementation(project(":ailegorreta-kit-commons:ailegorreta-kit-commons-security"))

    implementation("com.vaadin:vaadin-core:${property("vaadinVersion")}") {
        exclude( group="com.vaadin",  module="flow-polymer-template") /* Replace artifactId with vaadin-core to use only free components */
    }
    implementation("com.vaadin:vaadin-spring-boot-starter:${property("vaadinVersion")}") {
        exclude( group="com.vaadin",  module="vaadin-core") /* Excluding so that webjars are not included. */
    }
    implementation("eu.vaadinonkotlin:vok-framework-vokdb:${property("vok-framework-vokdbVersion")}")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
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
            artifactId = "ailegorreta-kit-client-components"
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
                name.set("ailegorreta-kit-client-components")
                description.set("Classes to implement security for front-end microservices")
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
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

// Validate any Vaadin deprecation
tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:deprecation")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
