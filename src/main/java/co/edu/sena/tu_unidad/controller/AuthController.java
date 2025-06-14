package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.LoginRequestDto;
import co.edu.sena.tu_unidad.dto.LoginResponseDto;
import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.dto.UserDto;
import co.edu.sena.tu_unidad.service.AuthService;
import co.edu.sena.tu_unidad.repository.UserRepository;
import co.edu.sena.tu_unidad.entity.UserEntity;
import co.edu.sena.tu_unidad.security.JwtTokenProvider;
import co.edu.sena.tu_unidad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ServerResponseDto login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Login successful")
                .data(response)
                .build();
    }

    @PostMapping("/register")
    public ServerResponseDto register(@RequestBody UserDto request) {
        // Verificar si el email ya existe antes de registrar
        if (userRepository.existsByEmail(request.getEmail())) {
            return ServerResponseDto.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("El correo electrónico ya está registrado")
                    .data(null)
                    .build();
        }
        
        UserDto response = userService.register(request);
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("User registered successfully")
                .data(response)
                .build();
    }

    @GetMapping("/check-email")
    public ServerResponseDto checkEmailExists(@RequestParam String email) {
        boolean exists = userRepository.existsByEmail(email);
        Map<String, Boolean> responseData = new HashMap<>();
        responseData.put("exists", exists);
        
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(exists ? "Email already exists" : "Email available")
                .data(responseData)
                .build();
    }


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @GetMapping("/oauth2/success")
    public void oauthSuccessRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        System.out.println("LLEGÓ");


        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Buscar usuario por email
        UserEntity entity = userRepository.findByEmail(email).orElse(null);

        // Si no existe, lo crea
        if (entity == null) {
            entity = new UserEntity();
            entity.setEmail(email);
            entity.setName(name);
            entity.setPassword("GOOGLE_LOGIN"); // Puedes dejarlo fijo o nulo
            //entity.setCreatedAt(new Date());

            entity = userRepository.save(entity);
        }

        // Generar el token JWT con tu método
        String jwt = tokenProvider.generateTokenWithEmail(email);

        // Redirigir al frontend con el token
        Long userId = entity.getId(); // Asegúrate de que no es null
        response.sendRedirect("https://irrigex-front.onrender.com/admin.html?token=" + jwt + "&email=" + email + "&id=" + userId);

    }
    @GetMapping("/login")
    public String loginPage() {
        return "Por favor inicia sesión con Google.";
    }


}
