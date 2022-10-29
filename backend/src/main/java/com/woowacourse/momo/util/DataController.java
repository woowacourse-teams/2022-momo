package com.woowacourse.momo.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataController {

    private final DataGenerator dataGenerator;

    public DataController(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @GetMapping("/generatedata")
    public void run() throws InterruptedException {
        dataGenerator.dbInit();
    }
}
