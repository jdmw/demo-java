package jd.demo.intellij.plugin.util;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class PsiFileUtil {

    private final AnActionEvent event ;
    private PsiFileUtil(AnActionEvent e){
        this.event = e ;
    }

    public static PsiClass getPsiClass(PsiFile file){
        if(file != null){
            for (PsiElement child : file.getChildren()) {
                if(child instanceof com.intellij.psi.PsiClass){
                    return (PsiClass)child ;
                }
            }
        }
        return null ;
    }


    public static void main(String[] args) {
        System.out.println("");
    }
}
