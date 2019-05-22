package com.wmp.demo.common;

import java.util.Comparator;
import java.util.List;

public class CharacterHandler {
    private static Comparator<String> ALPHABET_ORDER = (s1, s2) -> {
        int res = String.CASE_INSENSITIVE_ORDER.compare(s1, s2);
        if (res == 0) {
            return s1.compareTo(s2);
        }
        return res;
    };
    private static Comparator<Integer> NUMBER_ORDER = Comparator.naturalOrder();

    private CharacterHandler() {
        throw new AssertionError();
    }

    public static void sortAlphabet(List<String> list) {
        list.sort(ALPHABET_ORDER);
    }

    public static void sortNumber(List<Integer> list) {
        list.sort(NUMBER_ORDER);
    }

}
