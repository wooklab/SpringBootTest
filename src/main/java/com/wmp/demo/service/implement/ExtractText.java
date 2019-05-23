package com.wmp.demo.service.implement;

import com.wmp.demo.common.HttpHandler;
import com.wmp.demo.common.StringHandler;
import com.wmp.demo.dto.OutputResult;
import com.wmp.demo.dto.TargetDto;
import com.wmp.demo.function.MergeListData;
import com.wmp.demo.function.OutputData;
import com.wmp.demo.function.SeparatorStringIntoCharacterAndNumber;
import com.wmp.demo.service.Extract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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

        String refinedText = StringHandler.getRefinedText(context, isRemoveHtml);
        log.debug("refined txt: {}", refinedText);

        String sortedData = getSortedMergeData(refinedText);
        log.info("check: {}", sortedData);

        OutputData outputData = new OutputData(sortedData, targetDto.getOutputUnit());
        outputData.doProcess();
        outputResult.setQuotient(outputData.getQuotientValue());
        outputResult.setRemainder(outputData.getRemainderValue());
        return outputResult;
    }

    private String getSortedMergeData(String refinedText) {
        SeparatorStringIntoCharacterAndNumber separator = new SeparatorStringIntoCharacterAndNumber(refinedText);
        separator.doSeparateString(true);

        MergeListData merge = new MergeListData(separator.getCharacterList(), separator.getNumberList());
        merge.doMerge();

        return merge.getMergeData().toString();
    }

}
