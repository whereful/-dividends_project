package com.zerobase.dividends.service;

import com.zerobase.dividends.exception.impl.AlreadyExistUserException;
import com.zerobase.dividends.exception.impl.NoUserException;
import com.zerobase.dividends.exception.impl.UnMatchPasswordException;
import com.zerobase.dividends.model.Auth;
import com.zerobase.dividends.model.MemberEntity;
import com.zerobase.dividends.persist.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new NoUserException());
    }

    /**
     * exceptionhandler 작동함
     * @param member
     * @return
     */
    public MemberEntity register(Auth.SignUp member) {
        boolean exists = this.memberRepository.existsByUsername(member.getUsername());
        if (exists) {
            throw new AlreadyExistUserException();
        }
        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        MemberEntity memberEntity = this.memberRepository.save(member.toEntity());
        return memberEntity;
    }


    /**
     * exceptionhandler 작동함
     * @param member
     * @return
     */
    public MemberEntity authenticate(Auth.SignIn member) {
        MemberEntity user = this.memberRepository.findByUsername(member.getUsername())
                .orElseThrow(() -> new NoUserException());

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new UnMatchPasswordException();
        }

        return user;
    }
}
