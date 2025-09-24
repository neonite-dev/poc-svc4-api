package com.cjt.svc4.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.cjt.svc4.service.extranet.LoginService;
import com.cjt.svc4.utils.jwt.CustomLogoutFilter;
import com.cjt.svc4.utils.jwt.JWTFilter;
import com.cjt.svc4.utils.jwt.JWTUtil;
import com.cjt.svc4.utils.jwt.LoginFilter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final LoginService loginService;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, LoginService loginService) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.loginService = loginService;
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

            @Override
            public CorsConfiguration getCorsConfiguration(@SuppressWarnings("null") HttpServletRequest request) {

                CorsConfiguration configuration = new CorsConfiguration();
                // configuration.addAllowedOrigin("*");
                // configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                // configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                // configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8081"));
                // configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5000"));
                // configuration.setAllowedOrigins(Collections.singletonList("http://localhost:7000"));
                configuration.addAllowedOriginPattern("*");
                configuration.setAllowedMethods(Collections.singletonList("*"));
                configuration.setAllowCredentials(true);
                configuration.setAllowedHeaders(Collections.singletonList("*"));
                configuration.setMaxAge(3600L);
                configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                return configuration;
            }
        })));
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((auth) -> auth
            // .requestMatchers("/login", "/", "/join", "/test/**").permitAll()
            // 'Static Contents'의 경우, 익명 허용
            // thymeleaf 설정 허용이므로 api만 이용시 아래 제거
            .requestMatchers("/", "/index.html").permitAll()
            .requestMatchers("/favicon.ico").permitAll()
            .requestMatchers("/js/**", "/dashboard/js/**", "/dashboard/vendors/js/**").permitAll()
            .requestMatchers("/css/**", "/dashboard/css/**", "/dashboard/vendors/css/**").permitAll()
            .requestMatchers("/img/**", "/image/**", "/images/**", "/dashboard/images/**", "/dashboard/vendors/images/**").permitAll()
            .requestMatchers("/font/**", "/fonts/**").permitAll()
            .requestMatchers("/file/**", "/files/**").permitAll()
            .requestMatchers("/vendor/**").permitAll()
            .requestMatchers("/dashboard/vendors/iconfonts/mdi/css/**").permitAll()
            .requestMatchers("/dashboard/vendors/iconfonts/mdi/fonts/**").permitAll()
            .requestMatchers("/configuration/**", "/api-docs/**", "/v3/api-docs/**", "/swagger*/**").permitAll() // springdoc, swagger
            .requestMatchers("/webjars/**").permitAll()
            // thymeleaf 설정 허용이므로 api만 이용시 아래 제거 할것 or vue 로그인 redirect 후 token 발급 후 허용
            .requestMatchers("/crm/**").permitAll()
            .requestMatchers("/eduapp/**").permitAll()
            .requestMatchers("/static/**").permitAll()
            .requestMatchers("/vueapp", "/vueapp/assets/**").permitAll()
            .requestMatchers("/vueapp2", "/vueapp2/", "/vueapp2/assets/**", "/vueapp2/images/**", "/vueapp2/favicon*").permitAll()
            .requestMatchers("/vueapp2/api/extra/login/**").permitAll()
            // API 경로들에 대한 접근 경로 설정
            .requestMatchers("/api/extra/login/**").permitAll()
            .requestMatchers("/webdata/**").permitAll()
            .anyRequest().authenticated());

        // JWT 필터 검증 추가
        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, loginService), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CustomLogoutFilter(jwtUtil, loginService), LogoutFilter.class);
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

//         @Autowired
// 	UserDetailsServiceImpl userDetailsService;

// 	@Autowired
// 	private AuthEntryPointJwt unauthorizedHandler;

// 	@Bean
// 	public AuthTokenFilter authenticationJwtTokenFilter() {
// 		return new AuthTokenFilter();
// 	}

// 	// @Override
// 	// public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
// 	// 	authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
// 	// }

// 	// @Bean
// 	// @Override
// 	// public AuthenticationManager authenticationManagerBean() throws Exception {
// 	// 	return super.authenticationManagerBean();
// 	// }

// 	@Bean
//     public PasswordEncoder passwordEncoder() {
//             return new BCryptPasswordEncoder();
//     }
        
// //      @Override
// // 	protected void configure(HttpSecurity http) throws Exception {
// // 		http.cors().and().csrf().disable()
// // 			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
// // 			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
// // 			.authorizeRequests().antMatchers("/api/auth/**","/api/board/**").permitAll()
// // 			.antMatchers("/api/test/**").permitAll()
// // 			.anyRequest().authenticated();

// // 		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
// // 	}

// //     private final JwtTokenProvider jwtTokenProvider;

// //     @Autowired
// //     public SecurityConfig(JwtTokenProvider jwtTokenProvider){
// //         this.jwtTokenProvider = jwtTokenProvider;
// //     }
// //     @Bean
// //     protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
// //         httpSecurity.httpBasic().disable() // REST API는 UI를 사용하지 않으므로 기본설정을 비활성화

// //                 .csrf().disable() // REST API는 csrf 보안이 필요 없으므로 비활성화

// //                 .sessionManagement()
// //                 .sessionCreationPolicy(
// //                         SessionCreationPolicy.STATELESS) // JWT Token 인증방식으로 세션은 필요 없으므로 비활성화

// //                 .and()
// //                 .authorizeRequests() // 리퀘스트에 대한 사용권한 체크
// //                 .antMatchers("/sign-api/sign-in", "/sign-api/sign-up",
// //                         "/sign-api/exception").permitAll() // 가입 및 로그인 주소는 허용
// //                 .antMatchers(HttpMethod.GET, "/board-api/**").permitAll() // product로 시작하는 Get 요청은 허용

// //                 .antMatchers("**exception**").permitAll()

// //                 .anyRequest().hasRole("ADMIN") // 나머지 요청은 인증된 ADMIN만 접근 가능

// //                 .and()
// //                 .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
// //                 .and()
// //                 .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())

// //                 .and()
// //                 .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
// //                         UsernamePasswordAuthenticationFilter.class); // JWT Token 필터를 id/password 인증 필터 이전에 추가
// //         return httpSecurity.build();
// //     }

// //     @Bean
// //     public PasswordEncoder passwordEncoder(){
// //         return PasswordEncoderFactories.createDelegatingPasswordEncoder();
// //     }

// //     @Bean
// //     public WebSecurityCustomizer webSecurityCustomizer() {
// //         return (web) -> web.ignoring().antMatchers("/v2/api-docs",
// //                 "/swagger-resources/**",
// //                 "/swagger-ui.html",
// //                 "/webjars/**",
// //                 "/swagger/**",
// //                 "/sign-api/exception");
// //     }
}