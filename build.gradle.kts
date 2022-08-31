import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION") // https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    `java-gradle-plugin`
    alias(libs.plugins.gradle.publish)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktlint)
}

group = "org.sourcegrade"
version = file("version").readLines().first()

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleKotlinDsl())
    implementation(
        "org.jlleitschuh.gradle:ktlint-gradle:" +
            // sigh, it doesn't seem like there is a better way to depend on ktlint-gradle in the compile-classpath
            libs.plugins.ktlint.get().toString().substringAfter(':')
    )
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
