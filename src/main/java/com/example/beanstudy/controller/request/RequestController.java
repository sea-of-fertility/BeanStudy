package com.example.beanstudy.controller.request;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
@Scope(value = "request")
@Slf4j
public class RequestController {


    Thread thread = Thread.currentThread();
    private String test = "test1";

    @GetMapping("/test1")
    private String test1() throws InterruptedException {
        log.info("current thread name: {}, test1 method {}",thread.getName(), test);
        test = "test2";
        changeValue();
        return "requestScope Test!!";
    }


    private void changeValue() throws InterruptedException {
        Thread.sleep(500000);
        log.info("currentThreadName{}, changeValue test: {}", thread.getName() ,test);
    }
}
