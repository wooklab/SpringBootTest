package com.wmp.demo.controller;

import com.wmp.demo.dto.OutputResult;
import com.wmp.demo.dto.TargetDto;
import com.wmp.demo.service.Extract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DemoController {
    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    @Qualifier("extract_text")
    private final Extract<OutputResult, TargetDto> extract;

    public DemoController(Extract<OutputResult, TargetDto> extract) {
        this.extract = extract;
    }


    @GetMapping("")
    public String doDemo(Model model) {
        model.addAttribute("test","Hello World..!");
        return "demoPage";
    }

    @PostMapping("")
    public String extract(Model model, TargetDto targetDto) {
        log.debug("input target: {}", targetDto);

        OutputResult outputResult = extract.getOutputData(targetDto);
        if (outputResult.getQuotient() != null) {
            model.addAttribute("quotient", outputResult.getQuotient());
        }
        if (outputResult.getRemainder() != null) {
            model.addAttribute("remainder", outputResult.getRemainder());
        }
        return "demoPage";
    }

}
