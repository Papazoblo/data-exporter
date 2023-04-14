package studio.medvedev.dataproexporterstarter.annotation;

import org.apache.poi.ss.usermodel.BorderStyle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface BorderInfo {
    BorderStyle left() default BorderStyle.NONE;

    BorderStyle right() default BorderStyle.NONE;

    BorderStyle top() default BorderStyle.NONE;

    BorderStyle bottom() default BorderStyle.NONE;
}
