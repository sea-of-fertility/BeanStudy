package com.example.beanstudy.controller.applicaton;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
@Scope(value = "application")
@Slf4j
public class ApplicationController {


    private final String key = "key";
    private String currentValue = "startValue";

    @GetMapping("/{newName}")
    public String change(HttpServletRequest request, @PathVariable(value = "newName") String newName) {
        log.info("current test value {}", currentValue);
        request.getServletContext().setAttribute(key, newName);
        currentValue =  (String) request.getServletContext().getAttribute(key);
        log.info("test {}", currentValue);
        return currentValue;
    }


    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        return (String) request.getServletContext().getAttribute(key);
    }


}
