package com.zerobase.dividends.web;

import com.zerobase.dividends.model.Auth;
import com.zerobase.dividends.model.MemberEntity;
import com.zerobase.dividends.security.TokenProvider;
import com.zerobase.dividends.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    // 회원가입을 위한 API
    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody Auth.SignUp request) {
        MemberEntity result = this.memberService.register(request);
        return ResponseEntity.ok(result);
    }

    // 로그인을 위한 API
    @PostMapping("/signin")
    public ResponseEntity<?> signin(
            @RequestBody Auth.SignIn request) {
        MemberEntity memberEntity = this.memberService.authenticate(request);
        String token = this.tokenProvider.generateToken(memberEntity.getUsername(), memberEntity.getRoles());
        return ResponseEntity.ok(token);
    }

}
