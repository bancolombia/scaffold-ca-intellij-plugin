package co.com.bancolombia.actions

import co.com.bancolombia.extensions.addLine
import co.com.bancolombia.extensions.initGridBag
import co.com.bancolombia.utils.CommandExecutor
import co.com.bancolombia.utils.PipelineOptions
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.util.castSafelyTo
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.JComponent
import javax.swing.JPanel

class CreatePipelineDialog(private val project: Project) : DialogWrapper(true) {

    private val panel = JPanel(GridBagLayout())
    private val pipeline = ComboBox(PipelineOptions.values())

    init {
        init()
        title = "Generate Pipeline"
        panel.preferredSize = Dimension(300, 100)
    }

    override fun createCenterPanel(): JComponent {
        return panel.addLine("Type", pipeline, initGridBag())
    }

    override fun doOKAction() {
        val options =
            this.pipeline.selectedItem.castSafelyTo<PipelineOptions>() ?: PipelineOptions.GITHUB
        try {
            super.doOKAction()
        } finally {
            Messages.showInfoMessage(
                CommandExecutor(this.project.basePath.toString()).generatePipeline(options.name.lowercase()),
                "Console output"
            )
        }
    }
}
