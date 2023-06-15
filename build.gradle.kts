import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    `java-library`
    `maven-publish`
    id("org.springframework.boot") version "3.1.0" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
    id("java")
    kotlin("jvm") version "1.8.21" apply false
    kotlin("plugin.spring") version "1.8.21" apply false
}

allprojects {
    group = "com.ailegorreta"
    version = "2.0.0"

    tasks.withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}





