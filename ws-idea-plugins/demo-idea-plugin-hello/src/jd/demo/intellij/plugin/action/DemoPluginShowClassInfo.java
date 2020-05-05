package jd.demo.intellij.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;

public class DemoPluginShowClassInfo extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        // Set the availability based on whether a project is open
        Project project = e.getProject();
        PsiFile file = e.getData(PlatformDataKeys.PSI_FILE);
        e.getPresentation().setEnabledAndVisible(project != null && file != null);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {

        Project project = e.getData(PlatformDataKeys.PROJECT);
        PsiFile file = e.getData(PlatformDataKeys.PSI_FILE);
        for (PsiElement psiElement : file.getChildren()) {
            if(psiElement instanceof PsiClass) {
                PsiClass clazz = (PsiClass) psiElement;
                StringBuilder sb = new StringBuilder();
                sb.append("class  :").append(clazz.getQualifiedName())
                        .append("\nmethods:");
                for(PsiMethod method : clazz.getMethods()){
                    sb.append("\n     ").append(method.getName());
                }

                Messages.showMessageDialog(project, sb.toString(),"messageInfo",Messages.getInformationIcon());
            }
        }
    }
}
