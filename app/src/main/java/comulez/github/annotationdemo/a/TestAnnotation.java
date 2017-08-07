package comulez.github.annotationdemo.a;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Ulez on 2017/8/4.
 * Email：lcy1532110757@gmail.com
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {
    int id() default -1;

    String msg() default "msg-default";

}
