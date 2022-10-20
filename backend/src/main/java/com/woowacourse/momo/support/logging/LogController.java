package com.woowacourse.momo.support.logging;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.woowacourse.momo.support.logging.exception.LogException;

@Controller
public class LogController {

    @ExceptionHandler(value = {LogException.class})
    void logException(LogException e) {
        e.printStackTrace();
    }
}

