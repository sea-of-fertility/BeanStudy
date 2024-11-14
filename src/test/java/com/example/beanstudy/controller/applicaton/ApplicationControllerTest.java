package com.example.beanstudy.controller.applicaton;

import com.example.beanstudy.config.HelloConfig;
import com.example.beanstudy.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


@SpringBootTest
@AutoConfigureMockMvc
class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("설명")
    public void test() throws Exception{
        //given
        AnnotationConfigApplicationContext config1 = new AnnotationConfigApplicationContext(HelloConfig.class);
        AnnotationConfigApplicationContext config2 = new AnnotationConfigApplicationContext(HelloConfig.class);
        MemberService memberService1 = config1.getBean("memberService", MemberService.class);
        MemberService memberService2 = config2.getBean("memberService", MemberService.class);

        System.out.println(memberService1);
        System.out.println(memberService2);
//        mockMvc.perform(MockMvcRequestBuilders.get("/application/{id}", "1"))
//
//                .andDo(MockMvcResultHandlers.print());
        //when

        //then
    }

}