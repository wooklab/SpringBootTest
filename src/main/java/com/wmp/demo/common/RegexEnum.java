package com.wmp.demo.common;

public enum RegexEnum {
    HTML("<[\\/\\!]*?[^<>]*?>"),
    FINAL_EXCLUDE("[^0-9a-zA-Z]+")
    ;
    private final String regex;

    public String getRegex() {
        return regex;
    }
    RegexEnum(String regex) {
        this.regex = regex;
    }
}
