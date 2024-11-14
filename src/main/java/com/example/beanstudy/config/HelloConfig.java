package com.example.beanstudy.config;


import com.example.beanstudy.repository.MemberRepositoryImp;
import com.example.beanstudy.service.MemberServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfig {

    @Bean
    public MemberServiceImp memberService() {
        return new MemberServiceImp(new MemberRepositoryImp());
    }


}
