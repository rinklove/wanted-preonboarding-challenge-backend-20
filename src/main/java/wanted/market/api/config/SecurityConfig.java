package wanted.market.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wanted.market.api.config.jwt.JwtAuthFilter;
import wanted.market.api.config.jwt.JwtTokenProvider;
import wanted.market.api.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService customUserDetailService;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint customAuthenticationEntryPoint;

    private static final String[] WHITE_LIST = {
            "/api/member/**", "/api/swagger/**", "/api-docs", "swagger-custom.html",
            "/api/item/list", "/api/item/{itemNo}"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        //CSRF, CORS
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());

        //세션 무상태로 관리하도록 구성
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //FormLogin, httpBasic 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.addFilterBefore(new JwtAuthFilter(jwtTokenProvider, customUserDetailService), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling((exceptionHandling) ->
                exceptionHandling.accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint));

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(WHITE_LIST).permitAll()
                .anyRequest().authenticated());

        return http.build();
    }
}
