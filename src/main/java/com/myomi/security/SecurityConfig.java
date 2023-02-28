package com.myomi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.myomi.jwt.filter.JwtAuthenticationFilter;
import com.myomi.jwt.provider.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

/**
 * SecurityConfig는 Spring Security 설정을 위한 클래스.
 * @author rimsong
 *
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtTokenProvider jwtTokenProvider;

	@Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

	/**
	 * httpBasic().disable().csrf().disable(): rest api이므로 basic auth 및 csrf 보안을 사용하지 않는다는 설정
	 *  sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS): JWT를 사용하기 때문에 세션을 사용하지 않는다는 설정
	 *  antMatchers().permitAll(): 해당 API에 대해서는 모든 요청을 허가한다는 설정
	 *  antMatchers().hasRole("USER"): USER 권한이 있어야 요청할 수 있다는 설정
	 *  anyRequest().authenticated(): 이 밖에 모든 요청에 대해서 인증을 필요로 한다는 설정
	 *  addFilterBefore(new JwtAUthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class): JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행하겠다는 설정
	 *  passwordEncoder: JWT를 사용하기 위해서는 기본적으로 password encoder가 필요한데, 여기서는 Bycrypt encoder를 사용
	 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()     

                .cors()
                .and()
//                .formLogin().disable()

                // 시큐리티는 기본적으로 세션을 사용
                // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2Login()
//                .userInfoEndpoint()
//	            .userService(customOAuth2UserService)
//	        	.and()	
                .and()
                .authorizeRequests()


                .antMatchers("/", "/**").permitAll()
                .antMatchers("/health/**",
                        "/v1/user/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v2/api-docs/**").permitAll()

                .antMatchers("/api/v1/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/product/{prodNum}").hasRole("SELLER")
                .antMatchers(HttpMethod.DELETE, "/product/{prodNum}").hasRole("SELLER")
                // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                .antMatchers(HttpMethod.GET, "/product/list/*", "/product/{prodNum}").permitAll()
                .antMatchers("/user/login", "/user/signup", "/auth/**", "/oauth2/**").permitAll()
                .antMatchers("/product/add").hasRole("SELLER")
				.antMatchers("/cart").hasRole("USER")
				.antMatchers("/order/**").hasRole("USER")
                .antMatchers("/user/test", "/user/modify").hasRole("USER")
                .antMatchers("/user/info").hasAnyRole("USER","SELLER")
                .antMatchers("/api/**", "/login/**", "/oauth2/**").permitAll ()
                .anyRequest().authenticated()
                .and()
                //OAuth
                // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
