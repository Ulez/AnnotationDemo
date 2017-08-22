
package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import static com.google.auto.common.MoreElements.getPackage;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * Created by Ulez on 2017/8/21.
 * Email：lcy1532110757@gmail.com
 */


@AutoService(Processor.class)
public class ButterKnifeProcessor extends AbstractProcessor {
    private static final ClassName VIEW = ClassName.get("android.view", "View");
    private Elements elementUtils;
    private TypeName targetTypeName;
    private static final ClassName UNBINDER = ClassName.get("comulez.github.annotationdemo.ButterKnife", "Unbinder");
    private static final ClassName Button = ClassName.get("android.widget", "Button");
    private Set<? extends Element> elements;
    private Set<? extends Element> elementClicks;

    private ClassName bindingClassName;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        elements = env.getElementsAnnotatedWith(Bind.class);
        elementClicks = env.getElementsAnnotatedWith(OnClick.class);
        for (Element element : elements) {
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            TypeMirror typeMirror = enclosingElement.asType();
            targetTypeName = TypeName.get(typeMirror);
            if (targetTypeName instanceof ParameterizedTypeName) {
                targetTypeName = ((ParameterizedTypeName) targetTypeName).rawType;
            }
            String packageName = getPackage(enclosingElement).getQualifiedName().toString();
            String className = enclosingElement.getQualifiedName().toString().substring(packageName.length() + 1).replace('.', '$');
            bindingClassName = ClassName.get(packageName, className);
            l(targetTypeName.toString());
        }
        TypeSpec.Builder
                result = TypeSpec.classBuilder(bindingClassName.simpleName() + "_ViewBinding")
                .addModifiers(PUBLIC)
                .addSuperinterface(UNBINDER)
                .addField(targetTypeName, "target", PRIVATE)
                .addMethod(createBindingConstructorForActivity())
                .addMethod(createBindingConstructorForActivity2())
                .addMethod(createBindingUnbindMethod());
        JavaFile javaFile = JavaFile.builder(bindingClassName.packageName(), result.build())
                .addFileComment("Generated code from Butter Knife. Do not modify!")
                .build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void l(String s) {
        System.out.println("log=" + s);
    }

    private MethodSpec createBindingConstructorForActivity() {
        MethodSpec.Builder builderConstructor = MethodSpec.constructorBuilder()
                .addModifiers(PUBLIC)
                .addParameter(targetTypeName, "target")
                .addStatement("this(target, target.getWindow().getDecorView())");
        return builderConstructor.build();
    }

    static final ClassName UTILS = ClassName.get("comulez.github.annotationdemo.ButterKnife", "Utils");

    private MethodSpec createBindingConstructorForActivity2() {
        MethodSpec.Builder builderConstructor2 = MethodSpec.constructorBuilder()
                .addModifiers(PUBLIC)
                .addParameter(targetTypeName, "target", Modifier.FINAL)
                .addParameter(VIEW, "source")
                .addStatement("this.target = target")
                .addStatement("View view");
        for (Element element : elements) {
            builderConstructor2.addStatement("target.$L = $T.findRequiredViewAsType(source, R.id.$L, \"field '$L'\", $T.class)", element.getSimpleName(), UTILS, element.getSimpleName(), element.getSimpleName(), getRawType(element));
        }
        for (Element element : elementClicks) {
            int[] ss = element.getAnnotation(OnClick.class).value();
            for (int s : ss) {
                builderConstructor2.addStatement("Utils.findRequiredView(source, $L).setOnClickListener(new View.OnClickListener() {\n" +
                        "                @Override\n" +
                        "                public void onClick(View v) {\n" +
                        "                    target.onClick(v);\n" +
                        "                }\n" +
                        "            })", s);
            }
        }

        return builderConstructor2.build();
    }

    private MethodSpec createBindingUnbindMethod() {
        MethodSpec.Builder result = MethodSpec.methodBuilder("unbind")
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .addStatement("$T target = this.target", targetTypeName)
                .addStatement("if (target == null) throw new $T($S)", IllegalStateException.class, "Bindings already cleared.")
                .addStatement("this.target = null");
        for (Element element : elements) {
            result.addStatement("target.$L = null", element.getSimpleName());
        }
        return result.build();
    }

    public ClassName getRawType(Element element) {
        TypeMirror typeMirror = element.asType();
        TypeName type = TypeName.get(typeMirror);
        if (type instanceof ParameterizedTypeName) {
            return ((ParameterizedTypeName) type).rawType;
        }
        return (ClassName) type;
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(Bind.class);
        annotations.add(OnClick.class);
        return annotations;
    }
}

