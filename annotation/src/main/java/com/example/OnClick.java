package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Ulez on 2017/8/17.
 * Email：lcy1532110757@gmail.com
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClick {
    /** View IDs to which the method will be bound. */
    int[] value() default {-1};
}
