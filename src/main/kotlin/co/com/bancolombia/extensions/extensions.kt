package co.com.bancolombia.extensions

import co.com.bancolombia.utils.label
import com.intellij.openapi.util.SystemInfo
import com.intellij.uiDesigner.core.AbstractLayout
import com.intellij.util.ui.GridBag
import java.awt.GridBagConstraints
import java.awt.Insets
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel


fun String.soCommand() = if(SystemInfo.isWindows) "cmd /c gradlew.bat ${this.trim()}" else "./gradlew ${this.trim()}"

fun String.runCommand(basePath: String): String{
    val workingDir = File(basePath)
    val process = ProcessBuilder(*soCommand().split(" ").toTypedArray())
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()
    val reader = BufferedReader(InputStreamReader(process.inputStream))
        .lines()
        .collect(Collectors.joining("\n"))
    val consoleOutput = StringBuilder("${this.soCommand()}\n$reader")
    try {
        if (!process.waitFor(30, TimeUnit.SECONDS)) {
            process.destroy()
            consoleOutput.append("execution timed out: $this")
        }
    }catch (e : InterruptedException){
        consoleOutput.append("the process was interrupted: $this \n cause: ${e.message}")
    }
    if (process.exitValue() != 0) {
        consoleOutput.append("execution failed with code ${process.exitValue()}: $this")
    }
    println(consoleOutput.toString())
    return consoleOutput.toString()
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

fun JComponent.enabledComponent(){
    this.isEnabled=true
    this.isVisible=true
}

fun JComponent.disabledComponent(){
    this.isEnabled=false
    this.isVisible=false
}

fun JPanel.addLine(label : JLabel, component:JComponent, grid:GridBag):JPanel{
    this.add(label, grid.customNextLine())
    this.add(component, grid.customTab())
    return this
}
fun JPanel.addLine(label : String, component:JComponent, grid:GridBag):JPanel{
    this.add(label(label), grid.customNextLine())
    this.add(component, grid.customTab())
    return this
}
