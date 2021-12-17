package co.com.bancolombia.actions

import co.com.bancolombia.extensions.customNextLine
import co.com.bancolombia.extensions.customTab
import co.com.bancolombia.extensions.initGridBag
import co.com.bancolombia.utils.CommandExecutor
import co.com.bancolombia.utils.DriverAdapters
import co.com.bancolombia.utils.ModeOptions
import co.com.bancolombia.utils.label
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.util.castSafelyTo
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField


class CreateDriverAdaptersDialog(
    private val project: Project
) : DialogWrapper(true) {

    private val panel = JPanel(GridBagLayout())
    private val type = ComboBox(DriverAdapters.values())
    private val name = JTextField()
    private val secret = JCheckBox("secret", true)
    private val url = JTextField("http://example.com")
    private val mode = ComboBox(ModeOptions.values())
    private val options = mutableMapOf<String, String>()

    init {
        init()
        title = "Generate Driver Adapters"
        panel.preferredSize = Dimension(300, 250)

    }

    override fun doOKAction() {
        val type = this.type.selectedItem.castSafelyTo<DriverAdapters>() ?: DriverAdapters.NONE
        if (name.isEnabled) {
            options["name"] = name.text
        }
        if (url.isEnabled) {
            options["url"] = url.text
        }
        CommandExecutor(this.project).generateDriverAdapter(type, options)
        super.doOKAction()
    }

    override fun doValidate() :ValidationInfo? {
        val selected = this.type.selectedItem.castSafelyTo<DriverAdapters>()
        if (selected == DriverAdapters.NONE){
            return ValidationInfo("Please make a selection",type)
        }
        if (selected == DriverAdapters.GENERIC && name.text.isNullOrEmpty()){
            return ValidationInfo("Please enter a name",name)
        }
        if (selected == DriverAdapters.RESTCONSUMER && url.text.isNullOrEmpty()){
            return ValidationInfo("Please enter a name",url)
        }
        return null
    }

    override fun createCenterPanel(): JComponent {

        name.isEnabled = false
        secret.isEnabled = false
        url.isEnabled = false
        mode.isEnabled = false

        type.addActionListener {
            val selectedType = type.selectedItem.castSafelyTo<DriverAdapters>() ?: DriverAdapters.NONE
            name.isEnabled = false
            secret.isEnabled = false
            url.isEnabled = false
            mode.isEnabled = false
            options.clear()
            enableFields(selectedType)
        }
        mode.addActionListener {
            val selectedServer = mode.selectedItem.castSafelyTo<ModeOptions>() ?: ModeOptions.TEMPLATE
            options["mode"] = selectedServer.name.toLowerCase()
        }
        secret.addActionListener {
            val selectedType = secret.isSelected.toString()
            options["secret"] = selectedType
        }

        val gridBag = initGridBag()
        panel.add(label("Type"), gridBag.customNextLine())
        panel.add(type, gridBag.customTab())
        panel.add(label("Name"), gridBag.customNextLine())
        panel.add(name, gridBag.customTab())
        panel.add(label("Url"), gridBag.customNextLine())
        panel.add(url, gridBag.customTab())
        panel.add(secret, gridBag.customNextLine())
        panel.add(label("Mode"), gridBag.customNextLine())
        panel.add(mode, gridBag.customTab())

        return panel
    }

    private fun enableFields(type: DriverAdapters) = when (type) {
        DriverAdapters.GENERIC -> name.isEnabled = true
        DriverAdapters.JPA, DriverAdapters.MONGODB -> secret.isEnabled = true
        DriverAdapters.RESTCONSUMER -> url.isEnabled = true
        DriverAdapters.REDIS -> {
            mode.isEnabled = true
            secret.isEnabled = true
        }
        else -> {}

    }

}