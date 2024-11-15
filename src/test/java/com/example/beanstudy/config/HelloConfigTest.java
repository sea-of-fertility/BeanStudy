package com.example.beanstudy.config;

import com.example.beanstudy.service.MemberService;
import com.example.beanstudy.service.MemberServiceImp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;



@SpringBootTest
class HelloConfigTest {


    @Autowired
    MemberServiceImp memberService;

    @Test
    @DisplayName("모든 빈 확인하기")
    public void allBeanCheck() throws Exception{

        //given
        AnnotationConfigApplicationContext config = new AnnotationConfigApplicationContext(HelloConfig.class);

        //when
        String[] beanDefinitionNames = config.getBeanDefinitionNames();

        //then
        Arrays.stream(beanDefinitionNames).forEach(System.out::println);
    }

    @Test
    @DisplayName("싱글톤 작동방식 확인")
    public void singleton() throws Exception{

        //given
        AnnotationConfigApplicationContext config = new AnnotationConfigApplicationContext(HelloConfig.class);

        //when
        MemberService memberService1 = config.getBean("memberService", MemberService.class);
        MemberService memberService2 = config.getBean("memberService", MemberService.class);

        //then
        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }

    @Test
    @DisplayName("Thread not Safe")
    public void multiThread() throws Exception{
        //given
        //when
        Runnable user1 = memberService::click;
        Runnable user2 = memberService::click;



        //then
        user1.run();
        user2.run();
        Long count = memberService.getCount();
        Assertions.assertThat(count).isNotEqualTo(1L);
    }
}



