package org.sourcegrade.style

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.CheckstylePlugin
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin

class StylePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.apply<CheckstylePlugin>()
        val checkStyleConfiguration = checkNotNull(javaClass.classLoader.getResourceAsStream(CHECKSTYLE_FILE)) {
            "Could not find resource $CHECKSTYLE_FILE"
        }
        target.configure<CheckstyleExtension> {
            config = target.resources.text.fromString(checkStyleConfiguration.use { it.reader().readText() })
            maxWarnings = 0
            toolVersion = "10.0"
        }
        target.apply<KtlintPlugin>()
        target.configure<KtlintExtension> {
            enableExperimentalRules.set(true)
        }
    }

    companion object {
        private const val CHECKSTYLE_FILE = "config/checkstyle/checkstyle.xml"
    }
}
