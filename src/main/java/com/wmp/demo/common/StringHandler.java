package com.wmp.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringHandler {
    private static final Logger log = LoggerFactory.getLogger(StringHandler.class);

    private StringHandler() {
        throw new AssertionError();
    }

    public static boolean isStringEmpty(String inputStr) {
        if (inputStr == null || "".equals(inputStr.trim()) || "null".equalsIgnoreCase(inputStr))
            return true;
        return false;
    }

    public static String getRefinedText(String text, boolean isRemoveHtml) {
        if (isRemoveHtml) {
            String removeHtml = removeHTMLTags(text);
            return getOnlyEngOrNumber(removeHtml);
        }
        return StringHandler.getOnlyEngOrNumber(text);
    }

    public static String removeHTMLTags(String text) {
        return text.replaceAll(RegexEnum.HTML.getRegex(), "");
    }

    public static String getOnlyEngOrNumber(String text) {
        return text.replaceAll(RegexEnum.FINAL_EXCLUDE.getRegex(), "");
    }
}
