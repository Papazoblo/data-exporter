package studio.medvedev.dataproexporterstarter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Exported {
    String title() default "";

    short headerHeight() default 300;

    short dataHeight() default 300;
}
