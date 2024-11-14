package com.example.beanstudy.controller.singleton;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/singleton")
public class SingleTonController {


    private String test = "test1";


    @GetMapping("/test1")
    public String test(){
        System.out.println(test);
        test = "test2";
        return "singleton test";
    }


    @GetMapping("/test2")
    public String test2(){
        System.out.println(test);
        test = "test3";
        return "singleton test";
    }

}
