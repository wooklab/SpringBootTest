package com.wmp.demo.common;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class StringHandlerTest {
    private static final Logger log = LoggerFactory.getLogger(StringHandler.class);

    @Test
    public void 영어_숫자만_출력() {
        String context = HttpHandler.getHttpHTML("https://www.naver.com");
        String refinedText = StringHandler.removeHTMLTags(context);
        String finalText = StringHandler.getOnlyEngOrNumber(refinedText);
        String finalText2 = StringHandler.getOnlyEngOrNumber(context);
        log.info("정제 후 내용1: {}", refinedText);
        log.info("정제 후 내용2: {}", finalText);
        log.info("정제 후 내용3: {}", finalText2);
    }
}