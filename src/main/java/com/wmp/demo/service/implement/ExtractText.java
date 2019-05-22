package com.wmp.demo.service.implement;

import com.wmp.demo.common.CharacterHandler;
import com.wmp.demo.common.HttpHandler;
import com.wmp.demo.common.StringHandler;
import com.wmp.demo.dto.OutputResult;
import com.wmp.demo.dto.TargetDto;
import com.wmp.demo.service.Extract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Qualifier("extract_text")
public class ExtractText implements Extract<OutputResult, TargetDto> {
    private static final Logger log = LoggerFactory.getLogger(ExtractText.class);

    @Override
    public boolean validateParam(TargetDto targetDto) {
        if (StringHandler.isStringEmpty(targetDto.getUrl())) {
            return false;
        }
        if (targetDto.getOutputUnit() == null) {
            return false;
        }
        return true;
    }

    @Override
    public OutputResult getOutputData(TargetDto targetDto) {
        String url = targetDto.getUrl();
        String context = HttpHandler.getHttpHTML(url);
        OutputResult outputResult = new OutputResult();

        if (context == null) {
            outputResult.setQuotient("HTTP Connection Error..!");
            return outputResult;
        }
        boolean isRemoveHtml = "not_html".equalsIgnoreCase(targetDto.getOpt());

        String refinedTxt = getRefinedTxt(context, isRemoveHtml);
        String result = getFinalData(refinedTxt);

        log.info("check: {}", result);
        return outputResult;
    }

    private String getRefinedTxt(String context, boolean isRemoveHtml) {
        if (isRemoveHtml) {
            String removeHtml = StringHandler.removeHTMLTags(context);
            return StringHandler.getOnlyEngOrNumber(removeHtml);
        }
        return StringHandler.getOnlyEngOrNumber(context);
    }

    private String getFinalData(String txt) {
        String[] list = txt.split("");
        List<String> enList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        for (String s : list) {
            try {
                int tmp = Integer.parseInt(s);
                numList.add(tmp);
            } catch (NumberFormatException nfe) {
                enList.add(s);
            }
        }
        CharacterHandler.sortAlphabet(enList);
        log.debug("EN LIST: {}", enList);
        CharacterHandler.sortNumber(numList);
        log.debug("NUM LIST: {}", numList);
        return getMergingTxt(enList, numList);
    }

    private String getMergingTxt(List<String> enList, List<Integer> numList) {
        int enCount = enList.size();
        int numCount = numList.size();
        if (enCount > numCount) {
            return doMergeTxt(numCount, enCount, enList, numList);
        }
        return doMergeTxt(enCount, numCount, enList, numList);
    }

    private String doMergeTxt(int smallCnt, int bigCnt, List<String> enList, List<Integer> numList) {
        StringBuilder mergeTxt = new StringBuilder();
        for (int i = 0; i < smallCnt; i++) {
            mergeTxt.append(enList.get(i)).append(numList.get(i));
        }
        for (int j = bigCnt - smallCnt; j < bigCnt; j++) {
            mergeTxt.append(enList.get(j));
        }
        return mergeTxt.toString();
    }
}
