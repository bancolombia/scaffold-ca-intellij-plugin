package co.com.bancolombia.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class DeleteModuleAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        if (e.project == null) return
        val dialog = DeleteModuleDialog(e.project!!)
        dialog.showAndGet()
    }
}
