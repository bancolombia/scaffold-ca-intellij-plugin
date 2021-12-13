package co.com.bancolombia.actions

import co.com.bancolombia.extensions.runCommand
import co.com.bancolombia.utils.Language
import co.com.bancolombia.utils.ProjectCoverage
import co.com.bancolombia.utils.ProjectType
import co.com.bancolombia.utils.label
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBLabel
import com.intellij.uiDesigner.core.AbstractLayout
import com.intellij.util.ui.GridBag
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.io.FileWriter
import java.io.PrintWriter
import javax.swing.*


class CreateStructureDialog(
    private val project : Project
) : DialogWrapper(true) {

    private val panel = JPanel(GridBagLayout())
    private val projectPackage = JTextField("co.com.bancolombia")
    private val projectName = JTextField("Default")
    private val projectType = ComboBox(ProjectType.values())
    private val language = ComboBox(Language.values())
    private val projectCoverage = ComboBox(ProjectCoverage.values())
    private val lombok = JCheckBox("Lombok", true)

    init {
        init()
        title = "Generate Structure"
    }



    override fun doOKAction() {
        val language = this.language.selectedItem.toString()
        writeBuildGradleFile(language)
        val packageName = this.projectPackage.text
        val type = this.projectType.selectedItem.toString()
        val name = this.projectName.text
        val coverage = projectCoverage.selectedItem.toString()
        val lombok = this.lombok.isSelected.toString()
        val command =
            "ca --package=$packageName --type=$type --name=$name --coverage=$coverage --lombok=$lombok --language=$language"
        command.runCommand(this.project)
        super.doOKAction()
    }

    private fun writeBuildGradleFile(language: String) {
        var filename = "build.gradle"

        var code = """plugins {
    id "co.com.bancolombia.cleanArchitecture" version "2.0.0"
}""".trimIndent()

        if (language == "KOTLIN") {
            filename += ".kts"
            code = """plugins {
    id("co.com.bancolombia.cleanArchitecture") version "2.0.0"
}""".trimIndent()
        }

        val fileWriter = FileWriter("${project.basePath.toString()}/$filename")
        val printWriter = PrintWriter(fileWriter)
        printWriter.print(code)
        printWriter.close()
    }

    override fun createCenterPanel(): JComponent {
        panel.preferredSize = Dimension(300, 250)

        val gridBag = GridBag().setDefaultInsets(
            Insets(
                0,
                0,
                AbstractLayout.DEFAULT_VGAP,
                AbstractLayout.DEFAULT_HGAP
            )
        ) .setDefaultWeightX(1.0).setDefaultFill(GridBagConstraints.HORIZONTAL)
        panel.add(label("Name"), gridBag.nextLine().next().weightx(0.2))
        panel.add(projectName, gridBag.next().weightx(0.8))
        panel.add(label("Package"), gridBag.nextLine().next().weightx(0.2))
        panel.add(projectPackage, gridBag.next().weightx(0.8))
        panel.add(label("Type"), gridBag.nextLine().next().weightx(0.2))
        panel.add(projectType, gridBag.next().weightx(0.8))
        panel.add(label("Language"), gridBag.nextLine().next().weightx(0.2))
        panel.add(language, gridBag.next().weightx(0.8))
        panel.add(label("Coverage"), gridBag.nextLine().next().weightx(0.2))
        panel.add(projectCoverage, gridBag.next().weightx(0.8))
        panel.add(label(""), gridBag.nextLine().next().weightx(0.2))
        panel.add(lombok, gridBag.next().weightx(0.8))
        return panel
    }
}
