package com.wmp.demo.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MergeListData {
    private static final Logger log = LoggerFactory.getLogger(MergeListData.class);
    private final List<String> characterList;
    private final List<Integer> numberList;
    private final int characterListSize;
    private final int numberListSize;
    private final boolean isNumberListSizeBig;
    private StringBuilder mergeData;

    public StringBuilder getMergeData() {
        return mergeData;
    }

    public MergeListData(List<String> characterList, List<Integer> numberList) {
        this.characterList = characterList;
        this.numberList = numberList;
        this.characterListSize = characterList.size();
        this.numberListSize = numberList.size();
        this.isNumberListSizeBig = characterListSize < numberListSize;

        this.mergeData = new StringBuilder();



        log.debug("Set List, characterListSize = {}, numberListSize = {}", characterListSize, numberListSize);
    }


    public void doMerge() {
        if (this.isNumberListSizeBig) {
            mergeProcess(this.numberListSize, this.characterListSize);
            return;
        }
        mergeProcess(this.characterListSize, this.numberListSize);
    }

    private void mergeProcess(int bigCount, int smallCount) {
        for (int frontIdx = 0; frontIdx < smallCount; frontIdx++) {
            frontMerge(frontIdx);
        }

        for (int rearIdx = smallCount; rearIdx < bigCount; rearIdx++) {
            rearMerge(rearIdx);
        }
    }

    private void frontMerge(int frontIdx) {
        this.mergeData
                .append(this.characterList.get(frontIdx))
                .append(this.numberList.get(frontIdx));
    }

    private void rearMerge(int rearIdx) {
        if (isNumberListSizeBig) {
            this.mergeData.append(this.numberList.get(rearIdx));
        } else {
            this.mergeData.append(this.characterList.get(rearIdx));
        }
    }
}
