package co.com.bancolombia.extensions


import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SystemInfo
import com.intellij.uiDesigner.core.AbstractLayout
import com.intellij.util.ui.GridBag
import java.awt.GridBagConstraints
import java.awt.Insets
import java.io.File
import java.util.concurrent.TimeUnit

fun String.soCommand() = if(SystemInfo.isWindows) "cmd /c gradlew.bat $this" else "./gradlew $this"

fun String.runCommand(project: Project) {
    println(project.basePath.toString())
    println("execute : $this")

    val workingDir = File(project.basePath.toString())
    val process = ProcessBuilder(*soCommand().split(" ").toTypedArray())
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
    if (!process.waitFor(10, TimeUnit.SECONDS)) {
        process.destroy()
        throw RuntimeException("execution timed out: $this")
    }
    if (process.exitValue() != 0) {
        throw RuntimeException("execution failed with code ${process.exitValue()}: $this")
    }
}

fun Map<String,String>.joinOptions() : String = this.map { (key,value) ->  "--$key=$value" }
    .joinToString  (" ")

fun GridBag.customNextLine() = this.nextLine().next().weightx(0.2)

fun GridBag.customTab() = this.next().weightx(0.8)

fun initGridBag() = GridBag().setDefaultInsets(
    Insets(
        0,
        0,
        AbstractLayout.DEFAULT_VGAP,
        AbstractLayout.DEFAULT_HGAP
    )
) .setDefaultWeightX(1.0).setDefaultFill(GridBagConstraints.HORIZONTAL)