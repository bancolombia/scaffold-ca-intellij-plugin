package co.com.bancolombia.scaffoldcaintellijplugin.services

import com.intellij.openapi.project.Project
import co.com.bancolombia.scaffoldcaintellijplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
