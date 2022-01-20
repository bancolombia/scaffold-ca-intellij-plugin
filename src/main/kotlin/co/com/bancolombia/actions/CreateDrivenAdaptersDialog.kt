package co.com.bancolombia.actions

import co.com.bancolombia.extensions.addLine
import co.com.bancolombia.extensions.disabledComponent
import co.com.bancolombia.extensions.enabledComponent
import co.com.bancolombia.extensions.initGridBag
import co.com.bancolombia.utils.CommandExecutor
import co.com.bancolombia.utils.DrivenAdapters
import co.com.bancolombia.utils.ModeOptions
import co.com.bancolombia.utils.label
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


class CreateDrivenAdaptersDialog(
    private val project: Project
) : DialogWrapper(true) {

    private val panel = JPanel(GridBagLayout())
    private val type = ComboBox(DrivenAdapters.values())
    private val name = JTextField()
    private val secret = JCheckBox("secret", true)
    private val url = JTextField("https://example.com")
    private val mode = ComboBox(ModeOptions.values())
    private val options = mutableMapOf<String, String>()
    private val nameLabel = label("Name")
    private val urlLabel = label("URL")
    private val modeLabel = label("Mode")


    init {
        init()
        title = "Generate Driven Adapters"
        panel.preferredSize = Dimension(300, 100)
    }

    override fun doOKAction() {
        val type = this.type.selectedItem.castSafelyTo<DrivenAdapters>() ?: DrivenAdapters.NONE
        if (name.isEnabled) {
            options["name"] = name.text
        }
        if (url.isEnabled) {
            options["url"] = url.text
        }
        if (secret.isEnabled) {
            options["secret"] = secret.isSelected.toString()
        }
        try {
            super.doOKAction()
        } finally {
            Messages.showInfoMessage(
                CommandExecutor(this.project.basePath.toString()).generateDrivenAdapter(type, options),
                "Console Output"
            )
        }
    }

    override fun doValidate(): ValidationInfo? {
        val selected = this.type.selectedItem.castSafelyTo<DrivenAdapters>()
        if (selected == DrivenAdapters.NONE) {
            return ValidationInfo("Please make a selection", type)
        }
        if (selected == DrivenAdapters.GENERIC && name.text.isNullOrEmpty()) {
            return ValidationInfo("Please enter a name", name)
        }
        if (selected == DrivenAdapters.RESTCONSUMER && url.text.isNullOrEmpty()) {
            return ValidationInfo("Please enter a url", url)
        }
        return null
    }

    override fun createCenterPanel(): JComponent {
        disableComponents()
        type.addActionListener {
            val selectedType =
                type.selectedItem.castSafelyTo<DrivenAdapters>() ?: DrivenAdapters.NONE
            disableComponents()
            options.clear()
            enableFields(selectedType)
        }
        mode.addActionListener {
            val selectedMode = mode.selectedItem.castSafelyTo<ModeOptions>() ?: ModeOptions.TEMPLATE
            options["mode"] = selectedMode.name.toLowerCase()
        }
        secret.addActionListener {
            val selectedType = secret.isSelected.toString()
            options["secret"] = selectedType
        }

        val gridBag = initGridBag()

        return panel.addLine(label("Type"), type, gridBag)
            .addLine(nameLabel, name, gridBag)
            .addLine(urlLabel, url, gridBag)
            .addLine("", secret, gridBag)
            .addLine(modeLabel, mode, gridBag)
    }

    private fun disableComponents() {
        name.disabledComponent()
        nameLabel.disabledComponent()
        secret.disabledComponent()
        url.disabledComponent()
        urlLabel.disabledComponent()
        mode.disabledComponent()
        modeLabel.disabledComponent()
    }

    private fun enableFields(type: DrivenAdapters) = when (type) {
        DrivenAdapters.GENERIC -> {
            name.enabledComponent()
            nameLabel.enabledComponent()
        }
        DrivenAdapters.JPA, DrivenAdapters.MONGODB -> secret.enabledComponent()
        DrivenAdapters.RESTCONSUMER -> {
            url.enabledComponent()
            urlLabel.enabledComponent()
        }
        DrivenAdapters.REDIS -> {
            mode.enabledComponent()
            modeLabel.enabledComponent()
            secret.enabledComponent()
        }
        else -> null
    }

}
