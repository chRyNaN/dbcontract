package com.chryan.dbcontract;

import com.chrynan.dbcontract.Entity;
import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * A processor that retrieves classes annotated with {@link Entity} and creates a database contract interface for them.
 */
@AutoService(Processor.class)
public class EntityProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(Entity.class.getCanonicalName());
    }

    @Override
    public void init(ProcessingEnvironment env) {
        this.filer = env.getFiler();
        this.messager = env.getMessager();
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Entity.class)) {
            try {
                ContractClassCreator.create(new EntityClass((TypeElement) element)).writeTo(filer);
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Error creating contract class for element: " + element.getSimpleName().toString(), element);
            }
        }

        return false;
    }
}
