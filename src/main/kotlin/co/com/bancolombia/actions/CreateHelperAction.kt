package co.com.bancolombia.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import java.util.*
import java.util.stream.Collectors


class CreateHelperAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val projectName: String = e.project!!.name


        val vFiles = ProjectRootManager.getInstance(e.project!!).contentRootsFromAllModules
        val sourceRootsList = Arrays.stream(vFiles).map { obj: VirtualFile -> obj.url }
            .collect(Collectors.joining("\n"))
        Messages.showInfoMessage(
            "Source roots for the $projectName plugin:\n$sourceRootsList",
            "Project Properties"
        )

        if (e.project == null) return
        CreateHelperDialog(e.project!!).show()
    }
}
