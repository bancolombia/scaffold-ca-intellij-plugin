package co.com.bancolombia.actions

import co.com.bancolombia.extensions.customNextLine
import co.com.bancolombia.extensions.customTab
import co.com.bancolombia.extensions.initGridBag
import co.com.bancolombia.utils.CommandExecutor
import co.com.bancolombia.utils.PipelineOptions
import co.com.bancolombia.utils.label
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.util.castSafelyTo
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class CreateUseCaseDialog(private val project: Project) : DialogWrapper(true) {


    private val panel = JPanel(GridBagLayout())
    private val useCaseName = JTextField("useCaseName")

    init {
        init()
        title = "Generate Use Case"
        panel.preferredSize = Dimension(300, 100)
    }

    override fun doValidate() : ValidationInfo? {
        val selected = this.useCaseName.text
        if (selected.isNullOrEmpty()){
            return ValidationInfo("Enter a name for the use case",useCaseName)
        }
        return null
    }

    override fun createCenterPanel(): JComponent {
        val gridBag = initGridBag()
        panel.add(label("Name"), gridBag.customNextLine())
        panel.add(useCaseName, gridBag.customTab())
        return panel
    }

    override fun doOKAction() {
        val name = this.useCaseName.text
        CommandExecutor(this.project).generateUseCase(name)
        super.doOKAction()
    }
}