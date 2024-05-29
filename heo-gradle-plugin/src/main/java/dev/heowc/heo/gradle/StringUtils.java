package dev.heowc.heo.gradle;

import javax.annotation.Nullable;

public final class StringUtils {

    public static boolean isBlank(@Nullable String str) {
        return str == null || str.isBlank();
    }


    private StringUtils() {}
}
