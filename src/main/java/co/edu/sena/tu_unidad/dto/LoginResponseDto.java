package co.edu.sena.tu_unidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private Long id;
    private String username;
    private String fullName;
    private String role;
    private String token; // opcional, queda vacío si no usas JWT
}


// 