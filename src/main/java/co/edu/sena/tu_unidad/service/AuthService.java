package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.LoginRequestDto;
import co.edu.sena.tu_unidad.dto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto request) throws Exception;
}


// 