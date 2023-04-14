package studio.medvedev.dataproexporterstarter.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringUtils {

    public static boolean isNotBlank(String value) {
        return value != null && !value.isEmpty();
    }

    public static String changeCharset(String value) {
        if (Charset.defaultCharset().equals(Charset.forName("windows-1251"))) {
            return new String(value.getBytes(), StandardCharsets.UTF_8);
        }
        return value;
    }
}
