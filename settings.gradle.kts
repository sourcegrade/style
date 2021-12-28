rootProject.name = "Style"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val pluginPublishVersion: String by settings
        val ktlintVersion: String by settings
        kotlin("jvm") version kotlinVersion
        id("com.gradle.plugin-publish") version pluginPublishVersion
        id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
    }
}
