package studio.medvedev.dataproexporterstarter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface FontInfo {

    short size() default 10;

    String name() default "Arial";

    boolean bold() default false;
}
