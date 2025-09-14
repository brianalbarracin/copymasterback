package co.edu.sena.tu_unidad.service.impl;

import co.edu.sena.tu_unidad.dto.LoginRequestDto;
import co.edu.sena.tu_unidad.dto.LoginResponseDto;
import co.edu.sena.tu_unidad.entity.UserEntity;
import co.edu.sena.tu_unidad.repository.UserRepository;
import co.edu.sena.tu_unidad.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public LoginResponseDto login(LoginRequestDto request) throws Exception {
        UserEntity u = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));
        if (!encoder.matches(request.getPassword(), u.getPassword())) {
            throw new Exception("Clave inválida");
        }
        // token omitted (no JWT). If quieres token, lo añadimos aquí.
        return LoginResponseDto.builder()
                .id(u.getId())
                .username(u.getUsername())
                .fullName(u.getFullName())
                .role(u.getRole())
                .token(null)
                .build();
    }
}


// 