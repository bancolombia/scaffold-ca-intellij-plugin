package co.com.bancolombia.actions

import co.com.bancolombia.extensions.addLine
import co.com.bancolombia.extensions.initGridBag
import co.com.bancolombia.utils.CommandExecutor
import co.com.bancolombia.utils.Language
import co.com.bancolombia.utils.ProjectCoverage
import co.com.bancolombia.utils.ProjectType
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.ValidationInfo
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
        val coverage =
            projectCoverage.selectedItem.castSafelyTo<ProjectCoverage>()?.name ?: "JACOCO"
        val lombok = this.lombok.isSelected.toString()
        try {
            super.doOKAction()
        } finally {
            Messages.showInfoMessage(
                CommandExecutor(this.project).generateStructure(
                    mapOf(
                        "language" to language,
                        "package" to packageName,
                        "type" to type,
                        "name" to name,
                        "coverage" to coverage,
                        "lombok" to lombok
                    )
                ),
                "Console Output"
            )
        }
    }

    override fun doValidate(): ValidationInfo? {

        if (projectName.text.isNullOrEmpty()) {
            return ValidationInfo("Please enter a name for project", projectName)
        }
        if (projectPackage.text.isNullOrEmpty()) {
            return ValidationInfo("Please enter a name for package", projectPackage)
        }
        return null
    }

    override fun createCenterPanel(): JComponent {

        val gridBag = initGridBag()
        return panel.addLine("Name", projectName, gridBag)
            .addLine("Package", projectPackage, gridBag)
            .addLine("Type", projectType, gridBag)
            .addLine("Language", language, gridBag)
            .addLine("Coverage", projectCoverage, gridBag)
            .addLine("", lombok, gridBag)
    }
}
