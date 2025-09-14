package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.LoginRequestDto;
import co.edu.sena.tu_unidad.dto.LoginResponseDto;
import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.service.AuthService;
import co.edu.sena.tu_unidad.service.UserService;
import co.edu.sena.tu_unidad.entity.UserEntity;
import co.edu.sena.tu_unidad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import co.edu.sena.tu_unidad.dto.UserDto;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ServerResponseDto login(@RequestBody LoginRequestDto request) {
        try {
            LoginResponseDto resp = authService.login(request);
            return ServerResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .message("Login exitoso")
                    .data(resp)
                    .build();
        } catch (Exception e) {
            return ServerResponseDto.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Credenciales inválidas: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    @PostMapping("/register")
    public ServerResponseDto register(@RequestBody UserEntity user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ServerResponseDto.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("El nombre de usuario ya existe")
                    .data(null)
                    .build();
        }
        UserDto dto = UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .phone(user.getPhone())
                .build();
        UserDto created = userService.register(dto);
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("Usuario registrado")
                .data(created)
                .build();
    }
}


// 