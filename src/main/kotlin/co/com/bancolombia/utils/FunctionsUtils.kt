package co.com.bancolombia.utils

import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import javax.swing.JComponent

 fun label(textLabel: String): JBLabel {
    val label = JBLabel(textLabel)
    label.componentStyle = UIUtil.ComponentStyle.SMALL
    label.fontColor = UIUtil.FontColor.BRIGHTER
    label.border = JBUI.Borders.empty(0, 5, 2, 0)
    return label
}
