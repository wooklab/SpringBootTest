package com.wmp.demo.common;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class CharacterHandlerTest {
    private static Comparator<String> ALPHABET_ORDER = (o1, o2) -> {
        int res = String.CASE_INSENSITIVE_ORDER.compare(o1, o2);
        if (res == 0) {
            return o1.compareTo(o2);
        }
        return res;
    };

    private static Comparator<Integer> NUMBER_ORDER = Comparator.naturalOrder();
    @Test
    public void test() {
        List<String> testList = new ArrayList<>();
        testList.add("B");
        testList.add("a");
        testList.add("b");
        testList.add("A");
        testList.add("b");
        Collections.sort(testList, ALPHABET_ORDER);
        System.out.println(testList.toString());

        List<Integer> testList2 = new ArrayList<>();
        testList2.add(1);
        testList2.add(10);
        testList2.add(3);
        testList2.add(5);
        Collections.sort(testList2, Comparator.naturalOrder());
        System.out.println(testList2.toString());
    }
}