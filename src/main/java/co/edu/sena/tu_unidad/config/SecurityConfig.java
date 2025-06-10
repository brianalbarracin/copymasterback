package co.edu.sena.tu_unidad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import co.edu.sena.tu_unidad.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;

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
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login","/login/oauth2/**", "/auth/oauth2/success","/auth/oauth2/success","/auth/register", "/api/users/**","/products/**","/cart/**","/orders/**", "/addresses/**","/states").permitAll()  // tus endpoints públicos
                        .anyRequest().authenticated()
                ).oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler((request, response, authentication) -> {
                            System.out.println("✅ OAuth2 success handler ejecutado");

                            // Redirige manualmente
                            response.sendRedirect("/auth/oauth2/success");
                        })
                );


        return http.build();
    }



    // si usas jwt:
    // .oauth2ResourceServer(oauth2 -> oauth2.jwt());

  }

