package com.wmp.demo.function;

import com.wmp.demo.common.CharacterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeparatorStringIntoCharacterAndNumber {
    private static final Logger log = LoggerFactory.getLogger(SeparatorStringIntoCharacterAndNumber.class);
    private final String[] originStringArray;
    private List<String> characterList;
    private List<Integer> numberList;

    public List<String> getCharacterList() {
        return characterList;
    }

    public List<Integer> getNumberList() {
        return numberList;
    }

    public SeparatorStringIntoCharacterAndNumber(String originString) {
        this.originStringArray = originString.split("");
        this.characterList = new ArrayList<>();
        this.numberList = new ArrayList<>();
    }

    public void doSeparateString(boolean isSort) {
        Arrays.stream(originStringArray).forEach(this::separateString);
        if (isSort) {
            doSort();
        }
    }

    private void separateString(String s) {
        try {
            int tmp = Integer.parseInt(s);
            numberList.add(tmp);
        } catch (NumberFormatException nfe) {
            characterList.add(s);
        }
    }

    private void doSort() {
        CharacterHandler.sortAlphabet(characterList);
        log.debug("EN LIST: {}", characterList);
        CharacterHandler.sortNumber(numberList);
        log.debug("NUM LIST: {}", numberList);
    }

}
