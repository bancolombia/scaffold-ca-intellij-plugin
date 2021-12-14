package co.com.bancolombia.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateUseCaseAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        if (e.project == null) return
        val dialog = CreateUseCaseDialog(e.project!!)
        if(dialog.showAndGet())
        {
            dialog.close(23)
        }
    }
}
