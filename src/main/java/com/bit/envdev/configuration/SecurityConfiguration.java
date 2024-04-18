package com.bit.envdev.configuration;

import com.bit.envdev.jwt.JwtAutheticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
// @EnableWebSecurity: SpringSecurity의 FilterChain을 구성하기 위한 어노테이션
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAutheticationFilter jwtAutheticationFilter;
    private final String[] swaggerPath = {"/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/error"};
    private final String AUTH_PATH = "/api/auth/**";

    // 비밀번호 암호화 객체 bean 객체로 등록
    // 비밀번호 암호화와 UsernamePassworToken의 비밀번호와 CustomUserDetails 객체의 비밀번호를 비교하기 위한 passwordEncoder 객체 생성
    // 암호화된 비밀번호는 다시는 복호화할 수 없다.
    // PasswordEncoder에 있는 matches(암호화되지 않은 비밀번호, 암호화된 비밀번호)메소드를 이용해서 값을 비교한다. 일치하면 true, 일치하지 않으면 false
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // SpringSecurity의 FilterChain을 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // WebMvcConfiguration에서 설정해놨기 때문에 빈 상태로 설정
                .cors(httpSecurityCorsConfigurer -> {

                })
                .csrf(AbstractHttpConfigurer::disable)
                // 토큰방식으로 인증처리를 하기 때문에 basic 인증방식 비활성화
                .httpBasic(httpSecurityHttpBasicConfigurer -> {
                    httpSecurityHttpBasicConfigurer.disable();
                })
                // 토큰을 사용하기 때문에 세션 비활성화
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {

                    authorizationManagerRequestMatcherRegistry.requestMatchers(swaggerPath).permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers(AUTH_PATH).permitAll();
                    // anyRequest()에 대한 모든 권한 permitAll() 설정
                    // 각 요청에 대한 권한 설정 필요
                    // authorizationManagerRequestMatcherRegistry.requestMatchers("/contents/**").authenticated();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/board/**").hasAnyRole("ADMIN", "USER");
//                    authorizationManagerRequestMatcherRegistry.requestMatchers("/review/**").hasAnyRole("ADMIN", "USER");
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/mypage/**").authenticated();
//                    authorizationManagerRequestMatcherRegistry.requestMatchers("/notice/**").authenticated();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/report/state").hasAnyRole("ADMIN");
                    authorizationManagerRequestMatcherRegistry.anyRequest().permitAll();
                })
                // filter 등록
                // cosrFilter 동작 후 jwtAuthenticationFilter가 동작
                .addFilterAfter(jwtAutheticationFilter, CorsFilter.class)
                .build();

    }
}
