package comulez.github.annotationdemo.ButterKnife;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Ulez on 2017/8/17.
 * Email：lcy1532110757@gmail.com
 */


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bind {
    int id() default -1;
}
