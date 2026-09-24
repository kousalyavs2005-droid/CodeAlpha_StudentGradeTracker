package com.studentgradetracker.util;

public final class ValidationUtil {
    private ValidationUtil() {}

    public static int parseMark(String value) {
        try {
            int mark = Integer.parseInt(value.trim());
            if (mark < 0 || mark > 100) return -1;
            return mark;
        } catch (Exception e) {
            return -1;
        }
    }

    public static boolean validName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean validId(String id) {
        return id != null && !id.trim().isEmpty();
    }
}
