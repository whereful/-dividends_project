# 제로베이스 백엔드 스쿨 3차 실습 프로젝트

# 프로젝트 주요 사항
1. spring security version이 변함에 따라 코드 변경
2. java.lang.ClassNotFoundException: javax.xml.bind.DatatypeConverter 오류 해결
3. @PreAuthorize는 authorizeRequests 메소드에서 작동
4. Ant pattern과 MVC pattern


# 참고 링크

1. spring boot 2.x.x 과 spring boot 3.x.x 에서의 spring security 코드 차이 
- https://velog.io/@hwsa1004/SpringBoot-3.1.0-SpringSecurity-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0
- https://devhan.tistory.com/310

2. java.lang.ClassNotFoundException: javax.xml.bind.DatatypeConverter
- https://pekahblog.tistory.com/62

3. @PreAuthorize is not working when authorizeHttpRequests method is used in SecurityConfiguration Class
- https://github.com/spring-projects/spring-security/issues/12861

4-1. If Spring MVC is used, then by default auth.requestMatchers() will use the MvcRequestMatcher. 
Since H2 console is not controlled by Spring MVC, we must use AntPathRequestMatcher.
- https://github.com/spring-projects/spring-security/issues/12546
- https://www.inflearn.com/questions/836032/h2-console-403-%EC%97%90%EB%9F%AC

4-2. Factory method 'filterChain' threw exception with message: This method cannot decide whether these patterns are Spring MVC patterns or not.
If this endpoint is a Spring MVC endpoint, please use requestMatchers(MvcRequestMatcher);
otherwise, please use requestMatchers(AntPathRequestMatcher).
- https://stackoverflow.com/questions/76809698/spring-security-method-cannot-decide-pattern-is-mvc-or-not-spring-boot-applicati
