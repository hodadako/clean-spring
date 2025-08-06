plugins {
    java
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    id("org.springframework.boot") version "3.5.4"
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.spotbugs") version "6.1.11"
}

group = "com"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val mockitoAgent: Configuration = configurations.create("mockitoAgent")

dependencies {
    implementation(libs.bundles.spring)

    compileOnly(libs.lombok)
    developmentOnly(libs.spring.docker.compose)
    runtimeOnly(libs.bundles.database)

    annotationProcessor(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    testCompileOnly(libs.lombok)
    testRuntimeOnly(libs.junit.platform)
    testImplementation(libs.bundles.test)
    mockitoAgent(libs.mockito.core) { isTransitive = false }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("javax.persistence.Entity")
}

noArg {
    annotation("javax.persistence.Entity")
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("-javaagent:${mockitoAgent.asPath}")
}

spotbugs {
    excludeFilter.set(file("${projectDir}/spotbugs-exclude-filter.xml"))
}