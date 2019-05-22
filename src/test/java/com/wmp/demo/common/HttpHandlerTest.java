package com.wmp.demo.common;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class HttpHandlerTest {
    private static final Logger log = LoggerFactory.getLogger(HttpHandler.class);

    @Test
    public void HTML_테스트() {
        String url = "https://www.naver.com";

        String context = HttpHandler.getHttpHTML(url);

        log.info("context: {}", context);


    }

}