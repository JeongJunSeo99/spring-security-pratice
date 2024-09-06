package com.swcamp9th.springsecuritypratice.member.command.application.service;

import com.swcamp9th.springsecuritypratice.common.exception.CustomErrorCode;
import com.swcamp9th.springsecuritypratice.common.exception.CustomException;
import com.swcamp9th.springsecuritypratice.member.command.application.dto.req.ReqSignupDTO;
import com.swcamp9th.springsecuritypratice.member.command.domain.aggregate.entity.Member;
import com.swcamp9th.springsecuritypratice.member.command.domain.repository.MemberRepository;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "commandMemberService")
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberServiceImpl(ModelMapper modelMapper
                           , MemberRepository memberRepository)
//                           ,BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.modelMapper = modelMapper;
        this.memberRepository = memberRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void registMember(ReqSignupDTO reqSignupDTO) {

        log.info(reqSignupDTO.toString());

        if(!reqSignupDTO.isEmailApprove())
            throw new CustomException(CustomErrorCode.NO_APPROVE_EMAIL);

        Member member = modelMapper.map(reqSignupDTO, Member.class);
        member.setMemberUniqueId(UUID.randomUUID().toString());
        member.setEncryptedPassword(reqSignupDTO.getPassword());
//        member.setEncryptedPassword(bCryptPasswordEncoder.encode(memberDTO.getPassword()));
        memberRepository.save(member);

    }
}
