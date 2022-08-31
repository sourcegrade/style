import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    kotlin("jvm")
    id("com.gradle.plugin-publish")
    id("org.jlleitschuh.gradle.ktlint")
}

group = "org.sourcegrade"
version = file("version").readLines().first()

repositories {
    mavenCentral()
    gradlePluginPortal()
}

val ktlintVersion: String by project

dependencies {
    implementation(gradleKotlinDsl())
    implementation("org.jlleitschuh.gradle:ktlint-gradle:$ktlintVersion")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
}

gradlePlugin {
    plugins {
        create("style") {
            id = "org.sourcegrade.style"
            displayName = "Jagr Style"
            description = "Gradle plugin for consistent code style in SourceGrade repositories"
            implementationClass = "org.sourcegrade.style.StylePlugin"
        }
    }
}

pluginBundle {
    website = "https://www.sourcegrade.org"
    vcsUrl = "https://github.com/SourceGrade/Style"
    (plugins) {
        "style" {
            tags = listOf("style", "linter", "sourcegrade")
        }
    }
    mavenCoordinates {
        groupId = project.group.toString()
        artifactId = "style"
    }
}
