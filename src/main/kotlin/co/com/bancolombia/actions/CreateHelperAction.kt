package co.com.bancolombia.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateHelperAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        if (e.project == null) return
        val dialog = CreateHelperDialog(e.project!!)
        dialog.showAndGet()
    }
}
