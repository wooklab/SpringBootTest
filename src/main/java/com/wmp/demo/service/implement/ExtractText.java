package com.wmp.demo.service.implement;

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
        Map<String, List<Object>> characterListMap = getSeparateChars(refinedTxt);

        log.info("en: {}", characterListMap.get("en"));
        log.info("num: {}", characterListMap.get("num"));
        outputResult.setQuotient(characterListMap.get("en").toString());
        outputResult.setRemainder(characterListMap.get("num").toString());
        return outputResult;
    }

    private String getRefinedTxt(String context, boolean isRemoveHtml) {
        if (isRemoveHtml) {
            String removeHtml = StringHandler.removeHTMLTags(context);
            return StringHandler.getOnlyEngOrNumber(removeHtml);
        }
        return StringHandler.getOnlyEngOrNumber(context);
    }

    private Map<String, List<Object>> getSeparateChars(String txt) {
        String[] list = txt.split("");
        List<Object> enList = new ArrayList<>();
        List<Object> numList = new ArrayList<>();
        for (String s : list) {
            try {
                int tmp = Integer.parseInt(s);
                numList.add(tmp);
            } catch (NumberFormatException nfe) {
                enList.add(s);
            }
        }
        Map<String, List<Object>> returnMap = new HashMap<>();
        returnMap.put("en", enList);
        returnMap.put("num", numList);
        return returnMap;
    }
}
