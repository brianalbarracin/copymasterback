package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.LoginRequestDto;
import co.edu.sena.tu_unidad.dto.LoginResponseDto;
import co.edu.sena.tu_unidad.entity.UserEntity;
import co.edu.sena.tu_unidad.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    public LoginResponseDto login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        UserEntity user = userService.findByEmail(request.getEmail()).orElseThrow();

        return LoginResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .token(jwt)
                .role("USER") // Puedes implementar roles si es necesario
                .build();
    }
}
