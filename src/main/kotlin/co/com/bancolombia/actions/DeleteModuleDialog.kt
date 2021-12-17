package co.com.bancolombia.actions

import co.com.bancolombia.extensions.customNextLine
import co.com.bancolombia.extensions.customTab
import co.com.bancolombia.extensions.initGridBag
import co.com.bancolombia.utils.CommandExecutor
import co.com.bancolombia.utils.label
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class DeleteModuleDialog(private val project: Project) : DialogWrapper(true) {


    private val panel = JPanel(GridBagLayout())
    private val deleteModuleName = JTextField("moduleName")

    init {
        init()
        title = "Delete Module"
        panel.preferredSize = Dimension(300, 100)
    }

    override fun createCenterPanel(): JComponent {
        val gridBag = initGridBag()
        panel.add(label("Name"), gridBag.customNextLine())
        panel.add(deleteModuleName, gridBag.customTab())
        return panel
    }

    override fun doValidate() : ValidationInfo? {
        val selected = this.deleteModuleName.text
        if (selected.isNullOrEmpty()){
            return ValidationInfo("Please enter name of module to delete",deleteModuleName)
        }
        return null
    }

    override fun doOKAction() {
        val name = this.deleteModuleName.text
        CommandExecutor(this.project).deleteModule(name)
        super.doOKAction()
    }
}