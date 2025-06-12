package co.edu.sena.tu_unidad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import co.edu.sena.tu_unidad.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.setAllowedOrigins(List.of("https://irrigex-front.onrender.com"));
                    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfig.setAllowedHeaders(List.of("*"));
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/login",
                                "/auth/register",
                                "/auth/oauth2/success",
                                "/login/oauth2/**",
                                "/products/**",
                                "/api/users/**",
                                "/cart/**",
                                "/orders/**",
                                "/addresses/**",
                                "/states",
                                "/categories/**" // si tienes
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler((request, response, authentication) -> {
                            System.out.println("✅ OAuth2 success handler ejecutado");
                            response.sendRedirect("/auth/oauth2/success");
                        })
                );

        return http.build();
    }
}

