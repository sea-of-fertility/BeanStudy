package com.example.beanstudy.service;

import com.example.beanstudy.repository.MemberRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
public class MemberServiceImp implements MemberService{

    private final MemberRepository memberRepository;

    private Long count = 0L;

    public void logging() {
        log.info("{}", memberRepository.toString());
    }
    public MemberServiceImp(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Long click() {
        return count++;

    }
}
