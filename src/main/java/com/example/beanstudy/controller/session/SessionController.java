package com.example.beanstudy.controller.session;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
@Scope(value = "session")
@Slf4j
public class SessionController {
    private String test = "origin";


    @GetMapping("/test1")
    public String test(HttpServletRequest request) {
        log.info("session id: {}, test {}", request.getSession().getId(), test);

        test = "change";
        return "Session scope";
    }


}
