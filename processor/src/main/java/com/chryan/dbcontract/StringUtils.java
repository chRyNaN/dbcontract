package com.chryan.dbcontract;

/**
 * A utility class providing convenience methods for Strings.
 */
class StringUtils {

    private static final String GET = "get";
    private static final String SET = "set";
    private static final int GET_AND_SET_LENGTH = 3;
    private static final char UNDERSCORE = '_';

    static boolean isEmpty(String s) {
        return s == null || s.equals("");
    }

    static String getColumnNameFromMethodName(String methodName) {
        String withoutGetSet = methodName.startsWith(GET) || methodName.startsWith(SET) ? methodName.substring(GET_AND_SET_LENGTH) : methodName;

        return getColumnName(withoutGetSet);
    }

    static String getColumnName(String name) {
        StringBuilder sb = new StringBuilder();

        char[] charArray = name.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];

            if (Character.isUpperCase(c) && i != 0) {
                sb.append(UNDERSCORE);
                sb.append(c);
            } else {
                sb.append(Character.toUpperCase(c));
            }
        }

        return sb.toString();
    }

    static String getColumnFieldNameFromMethodName(String methodName) {
        return getColumnNameFromMethodName(methodName); // TODO Update?
    }

    static String getColumnFieldName(String name) {
        return getColumnName(name); // TODO Update?
    }

}
