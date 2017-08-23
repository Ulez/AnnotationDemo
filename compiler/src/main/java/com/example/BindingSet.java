package com.example;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Element;

/**
 * Created by Ulez on 2017/8/23.
 * Email：lcy1532110757@gmail.com
 */


public class BindingSet {
    public TypeName targetTypeName;
    public ClassName bindingClassName;

    public Set<Element> elements = new HashSet<>();
    public Set<Element> elementClicks = new HashSet<>();
}
