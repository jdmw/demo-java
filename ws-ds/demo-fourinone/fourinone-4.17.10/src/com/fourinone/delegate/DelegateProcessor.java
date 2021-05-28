package com.fourinone.delegate;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("Delegate")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class DelegateProcessor extends AbstractProcessor {
    private void note(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }
    
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for(TypeElement te:annotations){
            note("annotation:"+te.toString());
        }
        
        Set<? extends Element> elements = roundEnv.getRootElements();
        for(Element e:elements){
			List enclosedElems = e.getEnclosedElements();
			List<ExecutableElement> ees = ElementFilter.methodsIn(enclosedElems);
            for(ExecutableElement ee:ees){
				note("--ExecutableElement name is "+ee.getSimpleName());
                List<? extends AnnotationMirror> as = ee.getAnnotationMirrors();
				note("--as="+as);
                for(AnnotationMirror am:as){
					Map map= am.getElementValues();
					Set<ExecutableElement> ks = map.keySet();
                    for(ExecutableElement k:ks){
                        AnnotationValue av = (AnnotationValue)map.get(k);
                        note("----"+ee.getSimpleName()+"."+k.getSimpleName()+"="+av.getValue());
                    }
                }
            }
        }
        return false;
    }
}

