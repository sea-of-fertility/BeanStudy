package com.example.beanstudy.controller.prototype;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/prototype")
@Scope(value = "prototype")
@Slf4j
public class PrototypeController {


    private String test = "test";

    public PrototypeController() {
        log.info("PrototypeController instance created: {}", this);
    }

    @GetMapping("/test1")
    public String checkBeanName() {
        log.info("Current instance: {}, test: {}", this, test);
        test = "new test";
        return test;
    }
}
