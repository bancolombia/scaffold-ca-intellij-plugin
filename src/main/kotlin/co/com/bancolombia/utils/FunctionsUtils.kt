package co.com.bancolombia.utils

import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

 fun label(textLabel: String): JBLabel {
    val label = JBLabel(textLabel)
    label.componentStyle = UIUtil.ComponentStyle.SMALL
    label.fontColor = UIUtil.FontColor.BRIGHTER
    label.border = JBUI.Borders.empty(0, 5, 2, 0)
    return label
}

fun deleteGroovyGradleScripts(project: Project) {
   val pathOfBuildGradle = Paths.get("${project.basePath.toString()}/${CommandExecutor.BUILD_GRADLE}")
   val pathOfSettingsGradle = Paths.get("${project.basePath.toString()}/${CommandExecutor.SETTINGS_GRADLE}")
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
