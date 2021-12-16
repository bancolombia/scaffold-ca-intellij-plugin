package co.com.bancolombia.actions

import co.com.bancolombia.extensions.customNextLine
import co.com.bancolombia.extensions.customTab
import co.com.bancolombia.extensions.initGridBag
import co.com.bancolombia.utils.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.util.castSafelyTo
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField


class CreateStructureDialog(
    private val project: Project
) : DialogWrapper(true) {

    private val panel = JPanel(GridBagLayout())
    private val projectPackage = JTextField("co.com.bancolombia")
    private val language: ComboBox<Language> = ComboBox(Language.values())
    private val projectName = JTextField("Default")
    private val projectType = ComboBox(ProjectType.values())
    private val projectCoverage = ComboBox(ProjectCoverage.values())
    private val lombok = JCheckBox("Lombok", true)

    init {
        init()
        title = "Generate Structure"
        panel.preferredSize = Dimension(300, 250)
    }

    override fun doOKAction() {
        val language = this.language.selectedItem.castSafelyTo<Language>()?.name ?: "JAVA"
        val packageName = this.projectPackage.text
        val type = this.projectType.selectedItem.castSafelyTo<ProjectType>()?.name ?: "IMPERATIVE"
        val name = this.projectName.text
        val coverage = projectCoverage.selectedItem.castSafelyTo<ProjectCoverage>()?.name ?: "JACOCO"
        val lombok = this.lombok.isSelected.toString()

        CommandExecutor(this.project).generateStructure(
            mapOf(
                "language" to language,
                "package" to packageName, "type" to type, "name" to name, "coverage" to coverage, "lombok" to lombok
            )
        )

        super.doOKAction()
    }

    override fun createCenterPanel(): JComponent {

        val gridBag = initGridBag()
        panel.add(label("Name"), gridBag.customNextLine())
        panel.add(projectName, gridBag.customTab())
        panel.add(label("Package"), gridBag.customNextLine())
        panel.add(projectPackage, gridBag.customTab())
        panel.add(label("Type"), gridBag.customNextLine())
        panel.add(projectType, gridBag.customTab())
        panel.add(label("Language"), gridBag.customNextLine())
        panel.add(language, gridBag.customTab())
        panel.add(label("Coverage"), gridBag.customNextLine())
        panel.add(projectCoverage, gridBag.customTab())
        panel.add(label(""), gridBag.customNextLine())
        panel.add(lombok, gridBag.customTab())
        return panel
    }
}
