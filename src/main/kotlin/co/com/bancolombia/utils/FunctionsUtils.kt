package co.com.bancolombia.utils

import com.intellij.util.ui.JBUI
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.JLabel

fun label(textLabel: String): JLabel {
    val label = JLabel(textLabel)
    label.border = JBUI.Borders.empty(0, 5, 2, 0)
    return label
}

fun deleteGroovyGradleScripts(basePath: String) {
    val pathOfBuildGradle = Paths.get("${basePath}/${CommandExecutor.BUILD_GRADLE}")
    val pathOfSettingsGradle = Paths.get("${basePath}/${CommandExecutor.SETTINGS_GRADLE}")
    try {
        val wasDeletedBuildGradle = Files.deleteIfExists(pathOfBuildGradle)
        val wasDeleteSettingsGradle = Files.deleteIfExists(pathOfSettingsGradle)
        if (wasDeletedBuildGradle && wasDeleteSettingsGradle)
            println("File is deleted")
        else
            println("File does not exists")
    } catch (e: IOException) {
        println(e.message)
    }
}
