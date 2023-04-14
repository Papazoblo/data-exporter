package studio.medvedev.dataproexporterstarter.annotation;


import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import studio.medvedev.dataproexporterstarter.enums.CellDataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    FontInfo headerFont() default @FontInfo;

    FontInfo dataFont() default @FontInfo;

    BorderInfo borderInfo() default @BorderInfo;

    int width() default 100;

    short height() default 30;

    int maxWidth() default 500;

    boolean autoWidth() default false;

    String header() default "";

    CellDataType type() default CellDataType.GENERAL;

    String dataFormat() default "";

    HorizontalAlignment horizontalAlignment() default HorizontalAlignment.LEFT;

    VerticalAlignment verticalAlignment() default VerticalAlignment.CENTER;
}
