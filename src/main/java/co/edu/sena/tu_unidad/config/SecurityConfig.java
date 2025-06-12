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
import org.springframework.web.cors.CorsConfiguration;

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
        return NoOpPasswordEncoder.getInstance(); // Cambiar a BCrypt en producción
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("https://irrigex-front.onrender.com"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/products/**",              // <-- LIBRE
                                "/auth/login",
                                "/auth/register",
                                "/auth/oauth2/success",
                                "/login/oauth2/**",
                                "/categories/**",
                                "/addresses/**",
                                "/cart/**",
                                "/orders/**",
                                "/api/users/**",
                                "/states"
                        ).permitAll()
                        .anyRequest().authenticated()  // <-- todo lo demás sí protegido
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/auth/oauth2/success");
                        })
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("https://irrigex-front.onrender.com")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}